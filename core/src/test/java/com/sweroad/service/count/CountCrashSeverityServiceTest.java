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
public class CountCrashSeverityServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countCrashSeverityService;
    private List<Crash> crashes;
    private Log log = LogFactory.getLog(CountCrashSeverityServiceTest.class);

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        log.debug("testing that count crashes returns the correct number of records...");
        List<CountResult> crashCount = countCrashSeverityService.countCrashes(crashes);
        assertEquals(5, crashCount.size());
    }

    @Test
    public void testThatCountFatalCrashesReturnsCorrectNumber() {
        log.debug("testing that count fatal crashes returns the correct number...");
        List<CountResult> crashCount = countCrashSeverityService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertTrue(result.isPresent() && result.get().getCrashCount() == 2);
    }

    @Test
    public void testThatCountSeriousCrashesReturnsCorrectNumber() {
        log.debug("testing that count serious crashes returns the correct number...");
        List<CountResult> crashCount = countCrashSeverityService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertTrue(result.isPresent() && result.get().getCrashCount() == 4);
    }

    @Test
    public void testThatCountSlightCrashesReturnsCorrectNumber() {
        log.debug("testing that count slight crashes returns the correct number...");
        List<CountResult> crashCount = countCrashSeverityService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 3).findFirst();
        assertTrue(result.isPresent() && result.get().getCrashCount() == 1);
    }

    @Test
    public void testThatCountDamageOnlyCrashesReturnsCorrectNumber() {
        log.debug("testing that count damage only crashes returns the correct number...");
        List<CountResult> crashCount = countCrashSeverityService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 4).findFirst();
        assertTrue(result.isPresent() && result.get().getCrashCount() == 3);
    }

    @Test
    public void testThatCountUnknownCrashesReturnsCorrectNumber() {
        log.debug("testing that count crashes with unknown severity returns the correct number...");
        List<CountResult> crashCount = countCrashSeverityService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 5).findFirst();
        assertTrue(result.isPresent() && result.get().getCrashCount() == 0);
    }

    @Test
    public void testThatCountVehiclesInFatalCrashesReturnsCorrectNumber() {
        log.debug("testing that count vehicles in fatal crashes returns the correct number...");
        List<CountResult> crashCount = countCrashSeverityService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertTrue(result.isPresent() && result.get().getVehicleCount() == 4);
    }

    @Test
    public void testThatCountCasualtiesInSeriousCrashesReturnsCorrectNumber() {
        log.debug("testing that count casualties in serious crashes returns the correct number...");
        List<CountResult> crashCount = countCrashSeverityService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertTrue(result.isPresent() && result.get().getVehicleCount() == 8);
    }
}
