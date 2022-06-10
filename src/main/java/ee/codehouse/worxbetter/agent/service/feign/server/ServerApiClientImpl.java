package ee.codehouse.worxbetter.agent.service.feign.server;

import ee.codehouse.worxbetter.server.model.MowerStatus;
import ee.codehouse.worxbetter.server.model.QueryLogDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServerApiClientImpl implements ServerApiClient {
    private final AgentApiClient agentApiClient;

    @Override
    public boolean addStatus(MowerStatus currentStatus) {
        log.debug("Adding status");
        var result = false;
        var response = agentApiClient.addLatestStatus(currentStatus);
        result = HttpStatus.CREATED.equals(response.getStatusCode());
        return result;
    }

    @Override
    public boolean addQueryLog(QueryLogDto queryLog) {
        log.debug("Adding query log");
        var result = false;
        var response = agentApiClient.addQueryLog(queryLog);
        result = HttpStatus.CREATED.equals(response.getStatusCode());
        return result;
    }
}
