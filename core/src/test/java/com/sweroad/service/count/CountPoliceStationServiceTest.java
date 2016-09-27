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
public class CountPoliceStationServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countPoliceStationService;
    private List<Crash> crashes;

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        List<CountResult> crashCount = countPoliceStationService.countCrashes(crashes);
        assertEquals(4, crashCount.size());
    }

    @Test
    public void testThatCountKampalaCpsCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countPoliceStationService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 3).findFirst();
        assertEquals(7, result.get().getCrashCount());
    }

    @Test
    public void testThatCountLugaziPoliceStationCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countPoliceStationService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(3, result.get().getCrashCount());
    }

    @Test
    public void testThatCountMpigiPoliceStationCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countPoliceStationService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(0, result.get().getCrashCount());
    }

    @Test
    public void testThatCountVehiclesInKampalaCpsCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countPoliceStationService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 3).findFirst();
        assertEquals(19, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountVehiclesInLugaziPoliceStationCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countPoliceStationService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(6, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountCasualtiesInLugaziPoliceStationCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countPoliceStationService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(9, result.get().getCasualtyCount());
    }
}
