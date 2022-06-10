package ee.codehouse.worxbetter.agent.config;

import ee.codehouse.worxbetter.agent.client.ApiKeyRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerFeignClientConfiguration {
    @Value("${application.appId}")
    private String appId;

    @Bean
    ApiKeyRequestInterceptor apiKeyRequestInterceptor() {
        return new ApiKeyRequestInterceptor(appId);
    }
}
