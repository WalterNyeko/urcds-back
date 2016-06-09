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
public class CountRoadSurfaceServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countRoadSurfaceService;
    private List<Crash> crashes;
    private Log log = LogFactory.getLog(CountRoadSurfaceServiceTest.class);

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        log.debug("testing that count crashes returns the correct number of records...");
        List<CountResult> crashCount = countRoadSurfaceService.countCrashes(crashes);
        assertEquals(2, crashCount.size());
    }

    @Test
    public void testThatCountDrySurfaceCrashesReturnsCorrectNumber() {
        log.debug("testing that count Dry Surface crashes returns the correct number...");
        List<CountResult> crashCount = countRoadSurfaceService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(7, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountWetSurfaceCrashesReturnsCorrectNumber() {
        log.debug("testing that count Wet Surface crashes returns the correct number...");
        List<CountResult> crashCount = countRoadSurfaceService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(3, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountVehiclesInDrySurfaceCrashesReturnsCorrectNumber() {
        log.debug("testing that count vehicles in Dry Surface crashes returns the correct number...");
        List<CountResult> crashCount = countRoadSurfaceService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(20, result.get().getVehicleCount().longValue());
    }

    @Test
    public void testThatCountVehiclesInWetSurfaceCrashesReturnsCorrectNumber() {
        log.debug("testing that count vehicles in Wet Surface crashes returns the correct number...");
        List<CountResult> crashCount = countRoadSurfaceService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(5, result.get().getVehicleCount().longValue());
    }

    @Test
    public void testThatCountCasualtiesInDrySurfaceCrashesReturnsCorrectNumber() {
        log.debug("testing that count casualties in Dry Surface crashes returns the correct number...");
        List<CountResult> crashCount = countRoadSurfaceService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(14, result.get().getCasualtyCount().longValue());
    }
}
