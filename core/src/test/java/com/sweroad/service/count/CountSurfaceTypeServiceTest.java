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
public class CountSurfaceTypeServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countSurfaceTypeService;
    private List<Crash> crashes;

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        List<CountResult> crashCount = countSurfaceTypeService.countCrashes(crashes);
        assertEquals(4, crashCount.size());
    }

    @Test
    public void testThatCountTarmacRoadCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countSurfaceTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(7, result.get().getCrashCount());
    }

    @Test
    public void testThatCountMacadamRoadCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countSurfaceTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(2, result.get().getCrashCount());
    }

    @Test
    public void testThatCountMurramRoadCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countSurfaceTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 3).findFirst();
        assertEquals(0, result.get().getCrashCount());
    }

    @Test
    public void testThatCountUnspecifiedSurfaceTypeUnspecifiedCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countSurfaceTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == Constants.NOT_SPECIFIED_ID).findFirst();
        assertEquals(1, result.get().getCrashCount());
    }

    @Test
    public void testThatCountVehiclesInTarmacRoadCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countSurfaceTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(20, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountVehiclesInMacadamRoadCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countSurfaceTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(3, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountCasualtiesInDefectiveSurfaceCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countSurfaceTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(13, result.get().getCasualtyCount());
    }
}
