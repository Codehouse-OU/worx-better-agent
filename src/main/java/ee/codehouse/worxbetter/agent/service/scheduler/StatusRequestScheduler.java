package ee.codehouse.worxbetter.agent.service.scheduler;

import ee.codehouse.worxbetter.agent.service.feign.mower.MowerApiClient;
import ee.codehouse.worxbetter.agent.service.feign.server.ServerApiClient;
import ee.codehouse.worxbetter.agent.service.mapper.MowerStatusMapper;
import ee.codehouse.worxbetter.server.model.QueryLogDto;
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
    private final MowerStatusMapper mowerStatusMapper;

    @Scheduled(cron = "${application.scheduleInterval}")
    public void requestStatusUpdate() {
        log.debug("Requesting status update");
        var dateTime = OffsetDateTime.now();
        var queryLog = new QueryLogDto();
        queryLog.setTimestamp(dateTime);
        try {
            mowerApiClient.getCurrentStatus().ifPresent(currentStatus -> {
                log.debug("Got new status, sending to backend");
                var serverCurrentStatus = mowerStatusMapper.toEntity(currentStatus);
                var status = serverApiClient.addStatus(serverCurrentStatus);
                queryLog.setSuccessful(status);
            });
            log.debug("Sending querylog entry");
            serverApiClient.addQueryLog(queryLog);
        } catch (Exception e) {
            log.error("Unable to send data. Reason: {}", e.getMessage());
        }

    }
}
