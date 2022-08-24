package ee.codehouse.worxbetter.agent.service.feign.server;

import ee.codehouse.worxbetter.server.model.MowerStatus;
import ee.codehouse.worxbetter.server.model.QueryLogDto;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServerApiClientTest {
    @Mock
    private AgentApiClient agentApiClient;
    @InjectMocks
    private ServerApiClientImpl serverApiClient;

    @Test
    void addStatus() {
        when(agentApiClient.addLatestStatus(any())).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        var actual = serverApiClient.addStatus(new MowerStatus());

        assertThat(actual).isTrue();
    }

    @Test
    void addQueryLog() {
        when(agentApiClient.addQueryLog(any())).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        var actual = serverApiClient.addQueryLog(new QueryLogDto());

        assertThat(actual).isTrue();
    }

    @Test
    void addStatus_serverError() {
        doThrow(FeignException.class).when(agentApiClient).addLatestStatus(any());

        var actual = serverApiClient.addStatus(new MowerStatus());

        assertThat(actual).isFalse();
    }

    @Test
    void addQueryLog_serverError() {
        doThrow(FeignException.class).when(agentApiClient).addQueryLog(any());

        var actual = serverApiClient.addQueryLog(new QueryLogDto());

        assertThat(actual).isFalse();
    }
}
