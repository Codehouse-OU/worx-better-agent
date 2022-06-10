package ee.codehouse.worxbetter.agent.service.feign.server;

import ee.codehouse.worxbetter.agent.config.ServerFeignClientConfiguration;
import ee.codehouse.worxbetter.server.api.AgentApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="worxbetter", url="${application.mainServerUrl:http://192.168.1.217:8080}", configuration = ServerFeignClientConfiguration.class)
public interface AgentApiClient extends AgentApi {
}
