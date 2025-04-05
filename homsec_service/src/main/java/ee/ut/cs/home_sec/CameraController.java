package ee.ut.cs.home_sec;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CameraController {

    @GetMapping("/cameraAddress")
    public String getCameraAddress(){
        return "192.168.1.180:8080";
    }
}
