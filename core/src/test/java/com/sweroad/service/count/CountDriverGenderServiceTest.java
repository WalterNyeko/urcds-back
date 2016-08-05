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
 * Created by Frank on 7/18/16.
 */
public class CountDriverGenderServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countDriverGenderService;
    private List<Crash> crashes;

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        List<CountResult> crashCount = countDriverGenderService.countCrashes(crashes);
        assertEquals(4, crashCount.size());
    }

    @Test
    public void testThatCountCrashesWithMaleDriversReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverGenderService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(9, result.get().getCrashCount());
    }

    @Test
    public void testThatCountCrashesWithFemaleDriversReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverGenderService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(6, result.get().getCrashCount());
    }

    @Test
    public void testThatCountCrashesWithUnknownDriverGenderReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverGenderService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 3).findFirst();
        assertEquals(1, result.get().getCrashCount());
    }

    @Test
    public void testThatCountCrashesWithUnspecifiedDriverGenderReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverGenderService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == Constants.NOT_SPECIFIED_ID).findFirst();
        assertEquals(1, result.get().getCrashCount());
    }

    @Test
    public void testThatCountVehiclesWithMaleDriversReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverGenderService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(16, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountVehiclesWithFemaleDriversReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverGenderService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(6, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountCasualtiesInVehiclesWithMaleDriversReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverGenderService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(9, result.get().getCasualtyCount());
    }
}
