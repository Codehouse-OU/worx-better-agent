package ee.codehouse.worxbetter.agent.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class LandroidFeignClientConfiguration {
    @Value("${application.landroid.username:admin}")
    private String username;
    @Value("${application.landroid.pin:0000}")
    private String pin;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(username, pin);
    }

    /**
     * Add Jackson converter but override the content type it accepts, since the mower is returning text/html
     * content type instead of application/json
     * @return WorxConverter
     */
    @Bean
    public HttpMessageConverter<Object> worxConverter() {
        return new WorxHttpMessageConverter(Jackson2ObjectMapperBuilder.json().build());
    }

    static class WorxHttpMessageConverter extends AbstractJackson2HttpMessageConverter {
        public WorxHttpMessageConverter(ObjectMapper objectMapper) {
            super(objectMapper, MediaType.TEXT_HTML);
        }
    }
}
