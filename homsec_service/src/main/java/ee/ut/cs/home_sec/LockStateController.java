package ee.ut.cs.home_sec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LockStateController {
    private static final Logger LOG = LoggerFactory.getLogger(LockStateController.class);

    private volatile LockState lockState = LockState.OFF;

    @GetMapping("/home/lock-state")
    public LockState getLockState(){
        return lockState;
    }

    @PutMapping("/home/lock-state")
    public void setLockState(@RequestBody LockState lockState){
        LOG.info("setting lock state to " + lockState);
        this.lockState = lockState;
    }
}
