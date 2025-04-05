package ee.ut.cs.home_sec.mqtt;

import ee.ut.cs.home_sec.Alert;
import ee.ut.cs.home_sec.AlertService;
import ee.ut.cs.home_sec.AlertType;
import ee.ut.cs.home_sec.HomeSecurityApplication;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.time.Instant;

@Configuration
public class MotionAlertListener {

    private static final String MOTION_ALERT_TOPIC = "alert/motion";
    private static final String CLIENT_ID = "motion_alert_client_ws_" + HomeSecurityApplication.uuid.toString();
    @Value("${mqtt.host_uri}")
    private String mqttHost;

    private AlertService alertService;

    public MotionAlertListener(AlertService alertService){
        this.alertService = alertService;
    }

    @Bean
    public MessageChannel mqttMotionInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inboundMotionAlert(@Qualifier("mqttMotionInputChannel") MessageChannel messageChannel){
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(mqttHost, CLIENT_ID, MOTION_ALERT_TOPIC);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(messageChannel);
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttMotionInputChannel")
    public MessageHandler motionHandler() {
        return message -> alertService.addMotion(
            new Alert.Builder()
                .timestamp(Instant.now())
                .type(AlertType.MOTION.getValue())
                .message("Movement is detected at the door")
                .build()
        );
    }
}
