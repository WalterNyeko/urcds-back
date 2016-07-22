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
 * Created by Frank on 7/18/16.
 */
public class CountDriverAgeServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countDriverAgeService;
    private List<Crash> crashes;

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        List<CountResult> crashCount = countDriverAgeService.countCrashes(crashes);
        assertEquals(11, crashCount.size());
    }

    @Test
    public void testThatCountCrashesWithDriversAgedBetween18And24ReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverAgeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 4).findFirst();
        assertEquals(6, result.get().getCrashCount());
    }

    @Test
    public void testThatCountCrashesWithDriversAgedBetween25And34ReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverAgeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 5).findFirst();
        assertEquals(7, result.get().getCrashCount());
    }

    @Test
    public void testThatCountCrashesWithUnspecifiedDriverAgeReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverAgeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 0).findFirst();
        assertEquals(1, result.get().getCrashCount());
    }

    @Test
    public void testThatCountVehiclesWithDriversAgedBetween18And24ReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverAgeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 4).findFirst();
        assertEquals(9, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountVehiclesWithDriversAgedBetween35And44ReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverAgeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 6).findFirst();
        assertEquals(7, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountCasualtiesInVehiclesWithDriversAgedBetween25And34ReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverAgeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 5).findFirst();
        assertEquals(6, result.get().getCasualtyCount());
    }
}
