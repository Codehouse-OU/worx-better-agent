package ee.codehouse.worxbetter.agent.service.feign.server;

import ee.codehouse.worxbetter.server.model.CurrentStatus;
import ee.codehouse.worxbetter.server.model.QueryLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServerApiClientImpl implements ServerApiClient {
    private final WorxApiClient worxApiClient;

    @Override
    public boolean addStatus(CurrentStatus currentStatus) {
        var result = false;
        var response = worxApiClient.addLatestStatus(currentStatus);
        result = HttpStatus.CREATED.equals(response.getStatusCode());
        return result;
    }

    @Override
    public boolean addQueryLog(QueryLog queryLog) {
        var result = false;
        var response = worxApiClient.addQueryLog(queryLog);
        result = HttpStatus.CREATED.equals(response.getStatusCode());
        return result;
    }
}
