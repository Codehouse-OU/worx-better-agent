package ee.codehouse.worxbetter.agent.conf;

import ee.codehouse.worxbetter.agent.DataUtil;
import ee.codehouse.worxbetter.landroid.client.model.CurrentStatus;
import ee.codehouse.worxbetter.agent.service.feign.mower.MowerApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Optional;

@Configuration
public class FeignMockConfiguration {
    @Bean
    @Primary
    MowerApiClient mowerApiClient() {
        return () -> {
            var e = new CurrentStatus();
            e.setBatteryChargerState(DataUtil.CHARGER_STATE);
            e.setDistance(DataUtil.DISTANCE);
            e.setMessage(DataUtil.WORK_REQ);
            e.setPercBatt(String.valueOf(DataUtil.BATTERY));
            e.setState(DataUtil.STATE);
            e.setWorkReq(DataUtil.WORK_REQ);
            return Optional.of(e);
        };
    }
}
