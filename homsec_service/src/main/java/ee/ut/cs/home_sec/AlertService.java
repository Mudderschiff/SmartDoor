package ee.ut.cs.home_sec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertService {

    private static final Logger LOG = LoggerFactory.getLogger(AlertService.class);

    private static final int MAX_SIZE = 50;

    private final Deque<Alert> alerts = new LinkedList<>();

    private final Deque<Alert> motionAlerts = new LinkedList<>();
    private final Deque<Alert> knockAlerts = new LinkedList<>();
    private final Deque<Alert> rfidAlerts = new LinkedList<>();

    public void add(Alert alert){
        LOG.info("Adding alert: " + alert);
        synchronized (alerts) {
            alerts.add(alert);
            if (alerts.size() > MAX_SIZE) {
                alerts.removeLast();
            }
        }
    }

    public List<Alert> getRecentAlerts(){
        synchronized (alerts) {
            List<Alert> result = alerts.stream().collect(Collectors.toList());
            alerts.clear();
            return result;
        }
    }

    public void addMotion(Alert alert){
        LOG.info("Adding motion alert: " + alert);
        synchronized (motionAlerts) {
            motionAlerts.add(alert);
            if (motionAlerts.size() > MAX_SIZE) {
                motionAlerts.removeLast();
            }
        }
    }

    public List<Alert> getRecentMotionAlerts(){
        synchronized (motionAlerts) {
            List<Alert> result = motionAlerts.stream().collect(Collectors.toList());
            motionAlerts.clear();
            return result;
        }
    }

    public void addKnock(Alert alert){
        LOG.info("Adding knock alert: " + alert);
        synchronized (knockAlerts) {
            knockAlerts.add(alert);
            if (knockAlerts.size() > MAX_SIZE) {
                knockAlerts.removeLast();
            }
        }
    }

    public List<Alert> getRecentKnockAlerts(){
        synchronized (knockAlerts) {
            List<Alert> result = knockAlerts.stream().collect(Collectors.toList());
            knockAlerts.clear();
            return result;
        }
    }

    public void addRfid(Alert alert){
        LOG.info("Adding rfid alert: " + alert);
        synchronized (rfidAlerts) {
            rfidAlerts.add(alert);
            if (rfidAlerts.size() > MAX_SIZE) {
                rfidAlerts.removeLast();
            }
        }
    }

    public List<Alert> getRecentRfidAlerts(){
        synchronized (rfidAlerts) {
            List<Alert> result = rfidAlerts.stream().collect(Collectors.toList());
            rfidAlerts.clear();
            return result;
        }
    }
}
