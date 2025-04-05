package ee.ut.cs.home_sec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class HomeSecurityApplication {

    public static final UUID uuid = UUID.randomUUID();

    public static void main(String... args) {
        SpringApplication.run(HomeSecurityApplication.class, args);
    }

}
