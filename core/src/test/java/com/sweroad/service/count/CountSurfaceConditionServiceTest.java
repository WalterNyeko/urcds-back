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
public class CountSurfaceConditionServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countSurfaceConditionService;
    private List<Crash> crashes;
    private Log log = LogFactory.getLog(CountSurfaceConditionServiceTest.class);

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        log.debug("testing that count crashes returns the correct number of records...");
        List<CountResult> crashCount = countSurfaceConditionService.countCrashes(crashes);
        assertEquals(3, crashCount.size());
    }

    @Test
    public void testThatCountGoodSurfaceCrashesReturnsCorrectNumber() {
        log.debug("testing that count Good Surface crashes returns the correct number...");
        List<CountResult> crashCount = countSurfaceConditionService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(7, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountDefectiveSurfaceCrashesReturnsCorrectNumber() {
        log.debug("testing that count Defective Surface crashes returns the correct number...");
        List<CountResult> crashCount = countSurfaceConditionService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(3, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountBadSurfaceCrashesReturnsCorrectNumber() {
        log.debug("testing that count Bad Surface crashes returns the correct number...");
        List<CountResult> crashCount = countSurfaceConditionService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 3).findFirst();
        assertEquals(0, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountVehiclesInGoodSurfaceCrashesReturnsCorrectNumber() {
        log.debug("testing that count vehicles in Good Surface crashes returns the correct number...");
        List<CountResult> crashCount = countSurfaceConditionService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(20, result.get().getVehicleCount().longValue());
    }

    @Test
    public void testThatCountVehiclesInDefectiveSurfaceCrashesReturnsCorrectNumber() {
        log.debug("testing that count vehicles in Defective Surface crashes returns the correct number...");
        List<CountResult> crashCount = countSurfaceConditionService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(5, result.get().getVehicleCount().longValue());
    }

    @Test
    public void testThatCountCasualtiesInDefectiveSurfaceCrashesReturnsCorrectNumber() {
        log.debug("testing that count casualties in Defective Surface crashes returns the correct number...");
        List<CountResult> crashCount = countSurfaceConditionService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(8, result.get().getCasualtyCount().longValue());
    }
}
