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
public class CountCrashCauseServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countCrashCauseService;
    private List<Crash> crashes;

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        List<CountResult> crashCount = countCrashCauseService.countCrashes(crashes);
        assertEquals(20, crashCount.size());
    }

    @Test
    public void testThatCountCarelessOvertakingCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCrashCauseService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(4, result.get().getCrashCount());
    }

    @Test
    public void testThatCountTailGatingCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCrashCauseService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 4).findFirst();
        assertEquals(2, result.get().getCrashCount());
    }

    @Test
    public void testThatCountCrashesWithCarelessPedestriansReturnsCorrectNumber() {
        List<CountResult> crashCount = countCrashCauseService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 3).findFirst();
        assertEquals(0, result.get().getCrashCount());
    }

    @Test
    public void testThatCountUnspecifiedCrashCauseCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCrashCauseService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == Constants.NOT_SPECIFIED_ID).findFirst();
        assertEquals(1, result.get().getCrashCount());
    }

    @Test
    public void testThatCountVehiclesInCarelessOvertakingCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCrashCauseService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(7, result.get().getVehicleCount());
    }

    @Test
    public void testThatCountCasualtiesInCarelessOvertakingCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countCrashCauseService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(10, result.get().getCasualtyCount());
    }
}
