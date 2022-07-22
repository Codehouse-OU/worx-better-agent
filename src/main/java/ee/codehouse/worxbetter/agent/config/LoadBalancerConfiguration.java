package ee.codehouse.worxbetter.agent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
public class LoadBalancerConfiguration {
    @Value("#{'${application.landroid.url}'.split(',')}")
    private List<String> urls;
    @Bean
    public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(ConfigurableApplicationContext context) {
        return ServiceInstanceListSupplier.builder()
                .withBase(new LandroidInstanceSupplier())
                .withRetryAwareness()
                .build(context);
    }

    private class LandroidInstanceSupplier implements ServiceInstanceListSupplier {
        private static final String SERVICE_ID = "landroid";

        @Override
        public String getServiceId() {
            return SERVICE_ID;
        }

        @Override
        public Flux<List<ServiceInstance>> get() {
            List<ServiceInstance> defaultServiceInstance = urls.stream()
                    .map(url -> new DefaultServiceInstance(UUID.randomUUID().toString(), SERVICE_ID, url, 80, false))
                    .collect(Collectors.toList());
            return Flux.just(defaultServiceInstance);
        }
    }
}
