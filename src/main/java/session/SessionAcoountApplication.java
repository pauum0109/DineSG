package session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SessionAcoountApplication {
    public static void main(String[] args) {
        SpringApplication.run(SessionAcoountApplication.class, args);
    }
}