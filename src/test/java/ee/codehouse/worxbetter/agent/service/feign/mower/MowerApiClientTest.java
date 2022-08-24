package ee.codehouse.worxbetter.agent.service.feign.mower;

import ee.codehouse.worxbetter.landroid.client.model.CurrentStatus;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MowerApiClientTest {
    @Mock
    private LandroidApiClient landroidApiClient;
    @InjectMocks
    private MowerApiClientImpl mowerApiClient;

    @Test
    void getCurrentStatus_serverError() {
        var response = new ResponseEntity<CurrentStatus>(HttpStatus.BAD_REQUEST);
        when(landroidApiClient.getCurrentStatus()).thenReturn(response);

        var actual = mowerApiClient.getCurrentStatus();

        assertThat(actual).isEmpty();
    }

    @Test
    void getCurrentStatus_hasStatus() {
        var currentStatus = new CurrentStatus();
        var state = "OK";
        currentStatus.setState(state);
        var response = ResponseEntity.ok(currentStatus);
        when(landroidApiClient.getCurrentStatus()).thenReturn(response);

        var actual = mowerApiClient.getCurrentStatus();

        assertThat(actual).isPresent();
        assertThat(actual.get().getState()).isEqualTo(state);
    }

    @Test
    void getCurrentStatus_emptyResponse() {
        when(landroidApiClient.getCurrentStatus()).thenReturn(null);

        var actual = mowerApiClient.getCurrentStatus();

        assertThat(actual).isEmpty();
    }

    @Test
    void getCurrentStatus_feignException() {
        doThrow(FeignException.class).when(landroidApiClient).getCurrentStatus();

        var actual = mowerApiClient.getCurrentStatus();

        assertThat(actual).isEmpty();
    }
}
