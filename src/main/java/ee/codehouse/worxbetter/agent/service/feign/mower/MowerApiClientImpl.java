package ee.codehouse.worxbetter.agent.service.feign.mower;

import ee.codehouse.worxbetter.landroid.client.model.CurrentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MowerApiClientImpl implements MowerApiClient {
    private static final String PAYLOAD_TEMPLATE = "[[\"%s\",%d,%d]]";
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

    @Override
    public Optional<CurrentStatus> changeConfiguration(ConfigurationElement element, int value) {
        try {
            var urlencodedValue = URLEncoder.encode(String.format(PAYLOAD_TEMPLATE, element.getName(), element.getKey(), value), StandardCharsets.UTF_8);
            var response = landroidApiClient.postPayload("data=" + urlencodedValue);
            if (response != null && HttpStatus.OK.equals(response.getStatusCode())) {
                var body = response.getBody();
                return Optional.ofNullable(body);
            } else {
                log.error("Bad response status from Worx service");
            }
        } catch(Exception e) {
            log.error("Unable to query Worx service. Reason: {}", e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<CurrentStatus> startAction(ConfigurationElement element) {
        return changeConfiguration(element, 1);
    }
}
