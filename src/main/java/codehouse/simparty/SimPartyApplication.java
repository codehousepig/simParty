package codehouse.simparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SimPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimPartyApplication.class, args);
    }

}
