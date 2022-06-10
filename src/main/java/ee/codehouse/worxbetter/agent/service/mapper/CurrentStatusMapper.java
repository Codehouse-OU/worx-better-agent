package ee.codehouse.worxbetter.agent.service.mapper;

import ee.codehouse.worxbetter.landroid.client.model.CurrentStatus;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface CurrentStatusMapper extends EntityMapper<CurrentStatus, ee.codehouse.worxbetter.server.model.CurrentStatus> {
}
