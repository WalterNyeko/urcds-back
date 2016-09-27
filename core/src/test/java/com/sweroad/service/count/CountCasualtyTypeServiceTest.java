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
 * Created by Frank on 5/31/16.
 */
public class CountCasualtyTypeServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countCasualtyTypeService;
    private List<Crash> crashes;

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        List<CountResult> crashCount = countCasualtyTypeService.countCrashes(crashes);
        assertEquals(6, crashCount.size());
    }

    @Test
    public void testThatCountCrashesWithFatallyInjuredCasualtiesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCasualtyTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(4, result.get().getCrashCount());
    }

    @Test
    public void testThatCountCrashesWithSeriouslyInjuredCasualtiesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCasualtyTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(2, result.get().getCrashCount());
    }

    @Test
    public void testThatCountCrashesWithCasualtiesWithUnknownCasualtyTypeReturnsCorrectNumber() {
        List<CountResult> crashCount = countCasualtyTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 5).findFirst();
        assertEquals(0, result.get().getCrashCount());
    }

    @Test
    public void testThatCountVehiclesWithFatallyInjuredCasualtiesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCasualtyTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(2, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountVehiclesWithSlightlyInjuredCasualtiesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCasualtyTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 3).findFirst();
        assertEquals(5, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountSlightlyInjuredCasualtiesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCasualtyTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 3).findFirst();
        assertEquals(5, result.get().getCasualtyCount());
    }
}
