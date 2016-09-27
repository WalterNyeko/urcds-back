package com.sweroad.service.count;

import com.sweroad.Constants;
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
public class CountJunctionTypeServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countJunctionTypeService;
    private List<Crash> crashes;

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        List<CountResult> crashCount = countJunctionTypeService.countCrashes(crashes);
        assertEquals(7, crashCount.size());
    }

    @Test
    public void testThatCountNotInJunctionCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countJunctionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(6, result.get().getCrashCount());
    }

    @Test
    public void testThatCountRoundaboutCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countJunctionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 4).findFirst();
        assertEquals(3, result.get().getCrashCount());
    }

    @Test
    public void testThatCountCrossRoadsCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countJunctionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(0, result.get().getCrashCount());
    }

    @Test
    public void testThatCountUnspecifiedJunctionTypeCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countJunctionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == Constants.NOT_SPECIFIED_ID).findFirst();
        assertEquals(1, result.get().getCrashCount());
    }

    @Test
    public void testThatCountVehiclesInNotInJunctionCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countJunctionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(18, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountVehiclesInCrossRoadsCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countJunctionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 4).findFirst();
        assertEquals(5, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountCasualtiesInNotInJunctionCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countJunctionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(9, result.get().getCasualtyCount());
    }
}
