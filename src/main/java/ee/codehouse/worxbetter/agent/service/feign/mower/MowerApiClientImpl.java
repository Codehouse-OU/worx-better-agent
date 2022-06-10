package ee.codehouse.worxbetter.agent.service.feign.mower;

import ee.codehouse.worxbetter.landroid.client.model.CurrentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MowerApiClientImpl implements MowerApiClient {
    private final LandroidApiClient landroidApiClient;

    @Override
    public Optional<CurrentStatus> getCurrentStatus() {
        try {
            var response = landroidApiClient.getCurrentStatus();
            if (response != null && HttpStatus.OK.equals(response.getStatusCode())) {
                var body = response.getBody();
                return Optional.ofNullable(body);
            } else {
                log.error("Bad response status from Worx service");
            }
        } catch (Exception e) {
            log.error("Unable to query Worx service. Reason: {}", e.getMessage());
        }
        return Optional.empty();
    }
}
