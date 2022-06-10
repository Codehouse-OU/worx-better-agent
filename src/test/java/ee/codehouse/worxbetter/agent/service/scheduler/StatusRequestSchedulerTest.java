package ee.codehouse.worxbetter.agent.service.scheduler;

import ee.codehouse.worxbetter.agent.service.feign.mower.MowerApiClientImpl;
import ee.codehouse.worxbetter.agent.service.feign.server.ServerApiClientImpl;
import ee.codehouse.worxbetter.agent.service.mapper.MowerStatusMapperImpl;
import ee.codehouse.worxbetter.landroid.client.model.CurrentStatus;
import ee.codehouse.worxbetter.server.model.QueryLogDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatusRequestSchedulerTest {
    private static final String PERC_BATT = "12";
    private static final String IDLE = "idle";
    private static final long DISTANCE = 100L;
    private static final String MSG = "msg";
    private static final String STATE = "state";
    private static final String WORK_REQ = "workReq";
    @Mock
    private ServerApiClientImpl mockServerApiClient;
    @Mock
    private MowerApiClientImpl mockMowerApiClient;
    @Mock
    private MowerStatusMapperImpl currentStatusMapper;

    @InjectMocks
    private StatusRequestScheduler statusRequestScheduler;

    @Captor
    ArgumentCaptor<ee.codehouse.worxbetter.server.model.MowerStatus> mowerStatusArgumentCaptor;
    @Captor
    ArgumentCaptor<QueryLogDto> queryLogArgumentCaptor;

    @Test
    void shouldSendSuccessfulStatus() {
        when(mockMowerApiClient.getCurrentStatus()).thenReturn(Optional.of(currentStatus()));
        when(mockServerApiClient.addQueryLog(any())).thenReturn(true);
        when(mockServerApiClient.addStatus(any())).thenReturn(true);
        when(currentStatusMapper.toEntity((CurrentStatus) any())).thenCallRealMethod();
        statusRequestScheduler.requestStatusUpdate();

        verify(mockServerApiClient, times(1)).addStatus(mowerStatusArgumentCaptor.capture());


        verify(mockServerApiClient, times(1)).addQueryLog(queryLogArgumentCaptor.capture());
        assertThat(queryLogArgumentCaptor.getValue().getSuccessful()).isTrue();
    }

    private static CurrentStatus currentStatus() {
        var currentStatus = new CurrentStatus();
        currentStatus.setPercBatt(PERC_BATT);
        currentStatus.setBatteryChargerState(IDLE);
        currentStatus.setDistance(DISTANCE);
        currentStatus.setMessage(MSG);
        currentStatus.setState(STATE);
        currentStatus.setWorkReq(WORK_REQ);
        return currentStatus;
    }
}
