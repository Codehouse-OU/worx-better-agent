package ee.codehouse.worxbetter.agent.service.feign.server;

import ee.codehouse.worxbetter.server.api.WorxApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="worxbetter", url="${application.mainServerUrl:http://192.168.1.217:8011}")
public interface WorxApiClient extends WorxApi {
}
