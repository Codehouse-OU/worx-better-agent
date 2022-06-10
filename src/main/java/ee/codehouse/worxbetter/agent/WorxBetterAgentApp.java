package ee.codehouse.worxbetter.agent;

import ee.codehouse.worxbetter.agent.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class WorxBetterAgentApp {

    public static void main(String[] args) {SpringApplication.run(WorxBetterAgentApp.class, args);
    }

}
