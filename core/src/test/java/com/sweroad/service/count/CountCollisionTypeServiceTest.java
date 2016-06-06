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
import static org.junit.Assert.assertTrue;

/**
 * Created by Frank on 5/31/16.
 */
public class CountCollisionTypeServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countCollisionTypeService;
    private List<Crash> crashes;
    private Log log = LogFactory.getLog(CountCollisionTypeServiceTest.class);

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        log.debug("testing that count crashes returns the correct number of records...");
        List<CountResult> crashCount = countCollisionTypeService.countCrashes(crashes);
        assertEquals(9, crashCount.size());
    }

    @Test
    public void testThatCountHitPedestrianCrashesReturnsCorrectNumber() {
        log.debug("testing that count Hit Pedestrian crashes returns the correct number...");
        List<CountResult> crashCount = countCollisionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(2, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountHeadOnCrashesReturnsCorrectNumber() {
        log.debug("testing that count Head-on crashes returns the correct number...");
        List<CountResult> crashCount = countCollisionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 7).findFirst();
        assertEquals(2, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountUnknownCollisionTypeCrashesReturnsCorrectNumber() {
        log.debug("testing that count crashes with unknown collision type returns the correct number...");
        List<CountResult> crashCount = countCollisionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 9).findFirst();
        assertEquals(0, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountVehiclesInHeadOnCrashesReturnsCorrectNumber() {
        log.debug("testing that count vehicles in head-on crashes returns the correct number...");
        List<CountResult> crashCount = countCollisionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 7).findFirst();
        assertEquals(4, result.get().getVehicleCount().longValue());
    }

    @Test
    public void testThatCountVehiclesInAngleCollisionCrashesReturnsCorrectNumber() {
        log.debug("testing that count vehicles in angle collision crashes returns the correct number...");
        List<CountResult> crashCount = countCollisionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 6).findFirst();
        assertEquals(14, result.get().getVehicleCount().longValue());
    }

    @Test
    public void testThatCountCasualtiesInHeadOnCrashesReturnsCorrectNumber() {
        log.debug("testing that count casualties in head-on crashes returns the correct number...");
        List<CountResult> crashCount = countCollisionTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 7).findFirst();
        assertEquals(8, result.get().getCasualtyCount().longValue());
    }
}
