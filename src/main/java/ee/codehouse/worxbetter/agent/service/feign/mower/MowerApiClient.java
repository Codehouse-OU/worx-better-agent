package ee.codehouse.worxbetter.agent.service.feign.mower;

import ee.codehouse.worxbetter.landroid.client.model.CurrentStatus;

import java.util.Optional;

public interface MowerApiClient {
    Optional<CurrentStatus> getCurrentStatus();
}
