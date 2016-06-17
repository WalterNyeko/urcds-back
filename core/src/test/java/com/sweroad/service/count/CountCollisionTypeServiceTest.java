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
public class CountCollisionTypeServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countCollisionTypeService;
    private List<Crash> crashes;

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        List<CountResult> crashCount = countCollisionTypeService.countCrashes(crashes);
        assertEquals(10, crashCount.size());
    }

    @Test
    public void testThatCountHitPedestrianCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCollisionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(2, result.get().getCrashCount());
    }

    @Test
    public void testThatCountHeadOnCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCollisionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 7).findFirst();
        assertEquals(1, result.get().getCrashCount());
    }

    @Test
    public void testThatCountUnknownCollisionTypeCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCollisionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 9).findFirst();
        assertEquals(0, result.get().getCrashCount());
    }

    @Test
    public void testThatCountUnspecifiedCollisionTypeCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCollisionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 0).findFirst();
        assertEquals(1, result.get().getCrashCount());
    }

    @Test
    public void testThatCountVehiclesInHeadOnCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCollisionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 7).findFirst();
        assertEquals(2, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountVehiclesInAngleCollisionCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCollisionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 6).findFirst();
        assertEquals(14, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountCasualtiesInHeadOnCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCollisionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 7).findFirst();
        assertEquals(4, result.get().getCasualtyCount());
    }
}
