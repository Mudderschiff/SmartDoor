package ee.ut.cs.home_sec;

import ee.ut.cs.home_sec.mqtt.UnlockMessagePublisher.UnlockGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    private volatile Home home = new Home("bellasd");

    @Autowired
    private UnlockGateway unlockGateway;

    @GetMapping("/home")
    public Home getHome(){
        LOG.info("getting home");
        return home;
    }

    @PutMapping("/home")
    public void setHome(@RequestBody Home home){
        LOG.info("setting home to " + home);
        this.home = home;
    }
    @PutMapping("/home/unlock")
    public void unlock(){
        LOG.info("command unlock");
        unlockGateway.sendToMqtt("off");
    }
}
