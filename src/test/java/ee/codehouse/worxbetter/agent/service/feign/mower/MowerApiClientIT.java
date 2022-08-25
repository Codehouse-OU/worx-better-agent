package ee.codehouse.worxbetter.agent.service.feign.mower;

import ee.codehouse.worxbetter.agent.WorxBetterAgentApp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = WorxBetterAgentApp.class)
class MowerApiClientIT {
    @Autowired
    MowerApiClient mowerApiClient;

    @Test
    void changeConfiguration_mowAfterRain() {
        var value = 1;
        var currentStatus = mowerApiClient.changeConfiguration(ConfigurationElement.MOW_AFTER_RAIN, value);
        assertThat(currentStatus).isPresent();
        assertThat(currentStatus.get().getRitPioggia()).isEqualTo(value);
    }
    @Test
    void changeConfiguration_workingPercent() {
        var value = 100;
        var currentStatus = mowerApiClient.changeConfiguration(ConfigurationElement.WORKING_PERCENT, value);
        assertThat(currentStatus).isPresent();
        assertThat(currentStatus.get().getPercentProgrammatore()).isEqualTo(value);
    }
    @Test
    void startAction_stop() {
        var currentStatus = mowerApiClient.startAction(ConfigurationElement.STOP);
        assertThat(currentStatus).isPresent();
    }
    @Test
    void startAction_start() {
        var currentStatus = mowerApiClient.startAction(ConfigurationElement.START);
        assertThat(currentStatus).isPresent();
    }
    @Test
    void startAction_startZoneTraining() {
        var currentStatus = mowerApiClient.startAction(ConfigurationElement.ZONE_TRAINING);
        assertThat(currentStatus).isPresent();
    }
}
