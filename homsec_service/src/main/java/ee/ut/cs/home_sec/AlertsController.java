package ee.ut.cs.home_sec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class AlertsController {

    private static final Logger LOG = LoggerFactory.getLogger(AlertsController.class);

    private AlertService alertService;

    public AlertsController(AlertService alertService){
        this.alertService = alertService;
    }

    @GetMapping("/alerts")
    public List<Alert> getAlerts(){
        LOG.info("getting recent alerts");
        return alertService.getRecentAlerts();
    }

    @PostMapping("/alerts")
    public void addAlert(@RequestBody Alert alert){
        LOG.info("adding alert " + alert);
        alertService.add(alert);
    }

    @GetMapping("/alerts/motion")
    public List<Alert> getMotionAlerts(){
        LOG.info("getting recent motion alerts");
        return alertService.getRecentMotionAlerts();
    }

    @PostMapping("/alerts/motion")
    public void addMotionAlert(@RequestBody Alert alert){
        LOG.info("adding motion alert " + alert);
        alertService.addMotion(alert);
    }

    @GetMapping("/alerts/knock")
    public List<Alert> getKnockAlerts(){
        LOG.info("getting recent knock alerts");
        return alertService.getRecentKnockAlerts();
    }

    @PostMapping("/alerts/knock")
    public void addKnockAlert(@RequestBody Alert alert){
        LOG.info("adding knock alert " + alert);
        alertService.addKnock(alert);
    }

    @GetMapping("/alerts/rfid")
    public List<Alert> getRfidAlerts(){
        LOG.info("getting recent rfid alerts");
        return alertService.getRecentRfidAlerts();
    }

    @PostMapping("/alerts/rfid")
    public void addRfidAlert(@RequestBody Alert alert){
        LOG.info("adding rfid alert " + alert);
        alertService.addRfid(alert);
    }
}
