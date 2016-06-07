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
public class CountJunctionTypeServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countJunctionTypeService;
    private List<Crash> crashes;
    private Log log = LogFactory.getLog(CountJunctionTypeServiceTest.class);

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        log.debug("testing that count crashes returns the correct number of records...");
        List<CountResult> crashCount = countJunctionTypeService.countCrashes(crashes);
        assertEquals(6, crashCount.size());
    }

    @Test
    public void testThatCountNotInJunctionCrashesReturnsCorrectNumber() {
        log.debug("testing that count Not In Junction crashes returns the correct number...");
        List<CountResult> crashCount = countJunctionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(6, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountRoundaboutCrashesReturnsCorrectNumber() {
        log.debug("testing that count Roundabout crashes returns the correct number...");
        List<CountResult> crashCount = countJunctionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 4).findFirst();
        assertEquals(4, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountCrossRoadsCrashesReturnsCorrectNumber() {
        log.debug("testing that count cross roads crashes returns the correct number...");
        List<CountResult> crashCount = countJunctionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(0, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountVehiclesInNotInJunctionCrashesReturnsCorrectNumber() {
        log.debug("testing that count vehicles in Not In Junction crashes returns the correct number...");
        List<CountResult> crashCount = countJunctionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(18, result.get().getVehicleCount().longValue());
    }

    @Test
    public void testThatCountVehiclesInCrossRoadsCrashesReturnsCorrectNumber() {
        log.debug("testing that count vehicles in Cross Roads crashes returns the correct number...");
        List<CountResult> crashCount = countJunctionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 4).findFirst();
        assertEquals(7, result.get().getVehicleCount().longValue());
    }

    @Test
    public void testThatCountCasualtiesInNotInJunctionCrashesReturnsCorrectNumber() {
        log.debug("testing that count casualties in Not In Junction crashes returns the correct number...");
        List<CountResult> crashCount = countJunctionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(9, result.get().getCasualtyCount().longValue());
    }
}
