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
public class CountDriverBeltUseServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countDriverBeltUseService;
    private List<Crash> crashes;

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        List<CountResult> crashCount = countDriverBeltUseService.countCrashes(crashes);
        assertEquals(4, crashCount.size());
    }

    @Test
    public void testThatCountCrashesWithDriversWearingSeatBeltsReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverBeltUseService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(8, result.get().getCrashCount());
    }

    @Test
    public void testThatCountCrashesWithDriversNotWearingSeatBeltsReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverBeltUseService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 0).findFirst();
        assertEquals(8, result.get().getCrashCount());
    }

    @Test
    public void testThatCountCrashesWithUnknownDriversSeatBeltSituationReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverBeltUseService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(1, result.get().getCrashCount());
    }

    @Test
    public void testThatCountCrashesWithUnspecifiedDriverSeatBeltSituationReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverBeltUseService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == Constants.NOT_SPECIFIED_ID).findFirst();
        assertEquals(1, result.get().getCrashCount());
    }

    @Test
    public void testThatCountVehiclesWithDriversWearingSeatBeltsReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverBeltUseService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(11, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountVehiclesWithDriversNotWearingSeatsBeltReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverBeltUseService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 0).findFirst();
        assertEquals(11, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountCasualtiesInVehiclesWithDriversWearingSeatBeltsReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverBeltUseService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(8, result.get().getCasualtyCount());
    }
}
