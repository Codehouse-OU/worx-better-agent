package ee.codehouse.worxbetter.agent.service.feign.mower;

import ee.codehouse.worxbetter.agent.config.LandroidFeignClientConfiguration;
import ee.codehouse.worxbetter.agent.config.LoadBalancerConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="landroid", configuration = LandroidFeignClientConfiguration.class)
@LoadBalancerClient(name = "landroid", configuration = LoadBalancerConfiguration.class)
interface LandroidApiClient extends CustomLandroidApi {
}
