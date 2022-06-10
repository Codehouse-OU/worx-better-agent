package ee.codehouse.worxbetter.agent.service.feign.server;

import ee.codehouse.worxbetter.server.model.CurrentStatus;
import ee.codehouse.worxbetter.server.model.QueryLog;

public interface ServerApiClient {
    boolean addStatus(CurrentStatus currentStatus);
    boolean addQueryLog(QueryLog queryLog);
}
