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
public class CountRoadwayCharacterServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countRoadwayCharacterService;
    private List<Crash> crashes;
    
    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        List<CountResult> crashCount = countRoadwayCharacterService.countCrashes(crashes);
        assertEquals(5, crashCount.size());
    }

    @Test
    public void testThatCountBlindBendCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countRoadwayCharacterService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(6, result.get().getCrashCount());
    }

    @Test
    public void testThatCountStraightRoadCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countRoadwayCharacterService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(3, result.get().getCrashCount());
    }

    @Test
    public void testThatCountCurvedRoadCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countRoadwayCharacterService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 3).findFirst();
        assertEquals(0, result.get().getCrashCount());
    }

    @Test
    public void testThatCountUnspecifiedRoadwayCharacterCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countRoadwayCharacterService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 0).findFirst();
        assertEquals(1, result.get().getCrashCount());
    }

    @Test
    public void testThatCountVehiclesInBlindBendCrashesCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countRoadwayCharacterService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(18, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountVehiclesInWetSurfaceCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countRoadwayCharacterService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(5, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountCasualtiesInDrySurfaceCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countRoadwayCharacterService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(10, result.get().getCasualtyCount());
    }
}
