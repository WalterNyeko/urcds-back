package com.sweroad.service.count;

import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;
import com.sweroad.service.BaseManagerTestCase;
import com.sweroad.service.CrashService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Created by Frank on 5/31/16.
 */
public class CountVehicleFailureTypeServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countVehicleFailureTypeService;
    private List<Crash> crashes;
    private Log log = LogFactory.getLog(CountVehicleFailureTypeServiceTest.class);

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        log.debug("testing that count crashes returns the correct number of records...");
        List<CountResult> crashCount = countVehicleFailureTypeService.countCrashes(crashes);
        assertEquals(6, crashCount.size());
    }

    @Test
    public void testThatCountNoMechanicalConditionCrashesReturnsCorrectNumber() {
        log.debug("testing that count No Mechanical Condition crashes returns the correct number...");
        List<CountResult> crashCount = countVehicleFailureTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(7, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountBrakeFailureCrashesReturnsCorrectNumber() {
        log.debug("testing that count Brake Failure crashes returns the correct number...");
        List<CountResult> crashCount = countVehicleFailureTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 4).findFirst();
        assertEquals(3, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountTyreBlowoutCrashesReturnsCorrectNumber() {
        log.debug("testing that count Tyre Blowout crashes returns the correct number...");
        List<CountResult> crashCount = countVehicleFailureTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(0, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountVehiclesInNoMechanicalConditionCrashesReturnsCorrectNumber() {
        log.debug("testing that count vehicles in No Mechanical Condition crashes returns the correct number...");
        List<CountResult> crashCount = countVehicleFailureTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(13, result.get().getVehicleCount().longValue());
    }

    @Test
    public void testThatCountVehiclesInBrakeFailureCrashesReturnsCorrectNumber() {
        log.debug("testing that count vehicles in Brake Failure crashes returns the correct number...");
        List<CountResult> crashCount = countVehicleFailureTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 4).findFirst();
        assertEquals(12, result.get().getVehicleCount().longValue());
    }

    @Test
    public void testThatCountCasualtiesInBrakeFailureCrashesReturnsCorrectNumber() {
        log.debug("testing that count casualties in Brake Failure crashes returns the correct number...");
        List<CountResult> crashCount = countVehicleFailureTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(22, result.get().getCasualtyCount().longValue());
    }
}
