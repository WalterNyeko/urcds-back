package com.sweroad.service.count;

import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;
import com.sweroad.service.BaseManagerTestCase;
import com.sweroad.service.CrashService;
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

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        List<CountResult> crashCount = countVehicleFailureTypeService.countCrashes(crashes);
        assertEquals(7, crashCount.size());
    }

    @Test
    public void testThatCountNoMechanicalConditionCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countVehicleFailureTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(6, result.get().getCrashCount());
    }

    @Test
    public void testThatCountBrakeFailureCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countVehicleFailureTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 4).findFirst();
        assertEquals(3, result.get().getCrashCount());
    }

    @Test
    public void testThatCountTyreBlowoutCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countVehicleFailureTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(0, result.get().getCrashCount());
    }

    @Test
    public void testThatCountUnspecifiedVehicleFailureTypeCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countVehicleFailureTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 0).findFirst();
        assertEquals(1, result.get().getCrashCount());
    }


    @Test
    public void testThatCountVehiclesInNoMechanicalConditionCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countVehicleFailureTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(11, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountVehiclesInBrakeFailureCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countVehicleFailureTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 4).findFirst();
        assertEquals(12, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountCasualtiesInBrakeFailureCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countVehicleFailureTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(18, result.get().getCasualtyCount());
    }
}
