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
 * Created by Frank on 7/16/16.
 */
public class CountDriverCasualtyTypeServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countDriverCasualtyTypeService;
    private List<Crash> crashes;

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        List<CountResult> crashCount = countDriverCasualtyTypeService.countCrashes(crashes);
        assertEquals(6, crashCount.size());
    }

    @Test
    public void testThatCountCrashesWithFatallyInjuredDriversReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverCasualtyTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(2, result.get().getCrashCount());
    }

    @Test
    public void testThatCountCrashesWithSeriouslyInjuredDriversReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverCasualtyTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(3, result.get().getCrashCount());
    }

    @Test
    public void testThatCountCrashesWithUnspecifiedDriverInjuryReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverCasualtyTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == Constants.NOT_SPECIFIED_ID).findFirst();
        assertEquals(1, result.get().getCrashCount());
    }

    @Test
    public void testThatCountCrashesWithSlightlyInjuredDriversReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverCasualtyTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 3).findFirst();
        assertEquals(5, result.get().getCrashCount());
    }

    @Test
    public void testThatCountCrashesWithUninjuredDriversReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverCasualtyTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 4).findFirst();
        assertEquals(6, result.get().getCrashCount());
    }

    @Test
    public void testThatCountVehiclesWithFatallyInjuredDriversReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverCasualtyTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(2, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountVehiclesWithSeriouslyInjuredDriversReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverCasualtyTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(3, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountCasualtiesInVehiclesWithFatallyInjuredDriversReturnsCorrectNumber() {
        List<CountResult> crashCount = countDriverCasualtyTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(4, result.get().getCasualtyCount());
    }
}
