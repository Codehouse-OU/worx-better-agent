package ee.codehouse.worxbetter.agent.service.feign.server;

import ee.codehouse.worxbetter.server.model.MowerStatus;
import ee.codehouse.worxbetter.server.model.QueryLogDto;

public interface ServerApiClient {
    boolean addStatus(MowerStatus currentStatus);
    boolean addQueryLog(QueryLogDto queryLog);
}
