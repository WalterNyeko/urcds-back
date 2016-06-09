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
public class CountSurfaceTypeServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countSurfaceTypeService;
    private List<Crash> crashes;
    private Log log = LogFactory.getLog(CountSurfaceTypeServiceTest.class);

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        log.debug("testing that count crashes returns the correct number of records...");
        List<CountResult> crashCount = countSurfaceTypeService.countCrashes(crashes);
        assertEquals(3, crashCount.size());
    }

    @Test
    public void testThatCountTarmacRoadCrashesReturnsCorrectNumber() {
        log.debug("testing that count Tarmac Road crashes returns the correct number...");
        List<CountResult> crashCount = countSurfaceTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(7, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountMacadamRoadCrashesReturnsCorrectNumber() {
        log.debug("testing that count Macadam Road crashes returns the correct number...");
        List<CountResult> crashCount = countSurfaceTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(3, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountMurramRoadCrashesReturnsCorrectNumber() {
        log.debug("testing that count Murram Road crashes returns the correct number...");
        List<CountResult> crashCount = countSurfaceTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 3).findFirst();
        assertEquals(0, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountVehiclesInTarmacRoadCrashesReturnsCorrectNumber() {
        log.debug("testing that count vehicles in Tarmac Road crashes returns the correct number...");
        List<CountResult> crashCount = countSurfaceTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(20, result.get().getVehicleCount().longValue());
    }

    @Test
    public void testThatCountVehiclesInMacadamRoadCrashesReturnsCorrectNumber() {
        log.debug("testing that count vehicles in Macadam Road crashes returns the correct number...");
        List<CountResult> crashCount = countSurfaceTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(5, result.get().getVehicleCount().longValue());
    }

    @Test
    public void testThatCountCasualtiesInDefectiveSurfaceCrashesReturnsCorrectNumber() {
        log.debug("testing that count casualties in Defective Surface crashes returns the correct number...");
        List<CountResult> crashCount = countSurfaceTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(13, result.get().getCasualtyCount().longValue());
    }
}
