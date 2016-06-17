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
public class CountCrashSeverityServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countCrashSeverityService;
    private List<Crash> crashes;

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        List<CountResult> crashCount = countCrashSeverityService.countCrashes(crashes);
        assertEquals(6, crashCount.size());
    }

    @Test
    public void testThatCountFatalCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCrashSeverityService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(2, result.get().getCrashCount());
    }

    @Test
    public void testThatCountSeriousCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCrashSeverityService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(3, result.get().getCrashCount());
    }

    @Test
    public void testThatCountSlightCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCrashSeverityService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 3).findFirst();
        assertEquals(1, result.get().getCrashCount());
    }

    @Test
    public void testThatCountDamageOnlyCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCrashSeverityService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 4).findFirst();
        assertEquals(3, result.get().getCrashCount());
    }

    @Test
    public void testThatCountUnknownCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCrashSeverityService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 5).findFirst();
        assertEquals(0, result.get().getCrashCount());
    }

    @Test
    public void testThatCountUnspecifiedCrashSeverityCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCrashSeverityService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 0).findFirst();
        assertEquals(1, result.get().getCrashCount());
    }

    @Test
    public void testThatCountVehiclesInFatalCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCrashSeverityService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(4, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountVehiclesInSeriousCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCrashSeverityService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(6, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountCasualtiesInSeriousCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCrashSeverityService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(11, result.get().getCasualtyCount());
    }
}
