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
public class CountVehicleTypeServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countVehicleTypeService;
    private List<Crash> crashes;

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        List<CountResult> crashCount = countVehicleTypeService.countCrashes(crashes);
        assertEquals(17, crashCount.size());
    }

    @Test
    public void testThatCountMotorCarCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countVehicleTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(9, result.get().getCrashCount());
    }

    @Test
    public void testThatCountMotorcycleCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countVehicleTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 10).findFirst();
        assertEquals(1, result.get().getCrashCount());
    }

    @Test
    public void testThatCountMediumOmnibusCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countVehicleTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 7).findFirst();
        assertEquals(3, result.get().getCrashCount());
    }

    @Test
    public void testThatCountVehiclesWithUnspecifiedTypeReturnsCorrectNumber() {
        List<CountResult> crashCount = countVehicleTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 0).findFirst();
        assertEquals(1, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountMotorCarsReturnsCorrectNumber() {
        List<CountResult> crashCount = countVehicleTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(20, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountMediumOmnibusesReturnsCorrectNumber() {
        List<CountResult> crashCount = countVehicleTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 7).findFirst();
        assertEquals(3, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountCasualtiesInMediumOmnibusesReturnsCorrectNumber() {
        List<CountResult> crashCount = countVehicleTypeService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 7).findFirst();
        assertEquals(4, result.get().getCasualtyCount());
    }
}
