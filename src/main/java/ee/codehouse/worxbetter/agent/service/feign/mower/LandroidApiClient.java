package ee.codehouse.worxbetter.agent.service.feign.mower;

import ee.codehouse.worxbetter.agent.config.LandroidFeignClientConfiguration;
import ee.codehouse.worxbetter.landroid.client.api.LandroidApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="landroid", url="${application.landroid.url:http://192.168.1.171}", configuration = LandroidFeignClientConfiguration.class)
interface LandroidApiClient extends LandroidApi {
}
