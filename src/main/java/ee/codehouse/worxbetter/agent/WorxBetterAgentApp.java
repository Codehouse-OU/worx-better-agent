package ee.codehouse.worxbetter.agent;

import ee.codehouse.worxbetter.agent.config.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class WorxBetterAgentApp {
    private static final String NOT_CONFIGURED = "Not configured";
    private final Environment env;

    public WorxBetterAgentApp(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WorxBetterAgentApp.class);
        Environment env = app.run(args).getEnvironment();
        var appId = Optional
                .ofNullable(env.getProperty("application.appId"))
                .filter(StringUtils::hasLength)
                .orElse(NOT_CONFIGURED);
        var landroidIp = Optional
                .ofNullable(env.getProperty("application.landroid.url"))
                .filter(StringUtils::hasLength)
                .orElse(NOT_CONFIGURED);
        var serverIp = Optional
                .ofNullable(env.getProperty("application.mainServerUrl"))
                .filter(StringUtils::hasLength)
                .orElse(NOT_CONFIGURED);
        log.info(
                "\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running!\n\t" +
                        "App ID: {}\n\t" +
                        "Landroid IP: {}\n\t" +
                        "Server IP: {}\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                appId,
                landroidIp,
                serverIp,
                env.getActiveProfiles().length == 0 ? env.getDefaultProfiles() : env.getActiveProfiles()
        );
    }

}
