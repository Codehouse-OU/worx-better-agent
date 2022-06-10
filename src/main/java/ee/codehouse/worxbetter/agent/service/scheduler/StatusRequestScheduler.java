package ee.codehouse.worxbetter.agent.service.scheduler;

import ee.codehouse.worxbetter.agent.service.feign.mower.MowerApiClient;
import ee.codehouse.worxbetter.agent.service.feign.server.ServerApiClient;
import ee.codehouse.worxbetter.agent.service.mapper.CurrentStatusMapper;
import ee.codehouse.worxbetter.server.model.QueryLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatusRequestScheduler {
    private final ServerApiClient serverApiClient;
    private final MowerApiClient mowerApiClient;
    private final CurrentStatusMapper currentStatusMapper;

    @Scheduled(cron = "${application.scheduleInterval}")
    public void requestStatusUpdate() {
        log.debug("Requesting status update");
        var dateTime = OffsetDateTime.now();
        var queryLog = new QueryLog();
        queryLog.setTimestamp(dateTime);
        mowerApiClient.getCurrentStatus().ifPresent(currentStatus -> {
            var serverCurrentStatus = currentStatusMapper.toEntity(currentStatus);
            var status = serverApiClient.addStatus(serverCurrentStatus);
            queryLog.setSuccessful(status);
        });
        serverApiClient.addQueryLog(queryLog);
    }
}
