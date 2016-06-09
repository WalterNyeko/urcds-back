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

/**
 * Created by Frank on 5/31/16.
 */
public class CountWeatherServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countWeatherService;
    private List<Crash> crashes;
    private Log log = LogFactory.getLog(CountWeatherServiceTest.class);

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        log.debug("testing that count crashes returns the correct number of records...");
        List<CountResult> crashCount = countWeatherService.countCrashes(crashes);
        assertEquals(3, crashCount.size());
    }

    @Test
    public void testThatCountClearWeatherCrashesReturnsCorrectNumber() {
        log.debug("testing that count Clear Weather crashes returns the correct number...");
        List<CountResult> crashCount = countWeatherService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(7, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountRainyWeatherCrashesReturnsCorrectNumber() {
        log.debug("testing that count Rainy Weather crashes returns the correct number...");
        List<CountResult> crashCount = countWeatherService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(3, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountMistyWeatherCrashesReturnsCorrectNumber() {
        log.debug("testing that count Misty Weather crashes returns the correct number...");
        List<CountResult> crashCount = countWeatherService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 3).findFirst();
        assertEquals(0, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountVehiclesInClearWeatherCrashesReturnsCorrectNumber() {
        log.debug("testing that count vehicles in Clear Weather crashes returns the correct number...");
        List<CountResult> crashCount = countWeatherService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(13, result.get().getVehicleCount().longValue());
    }

    @Test
    public void testThatCountVehiclesInRainyWeatherCrashesReturnsCorrectNumber() {
        log.debug("testing that count vehicles in Rainy Weather crashes returns the correct number...");
        List<CountResult> crashCount = countWeatherService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(12, result.get().getVehicleCount().longValue());
    }

    @Test
    public void testThatCountCasualtiesInDefectiveSurfaceCrashesReturnsCorrectNumber() {
        log.debug("testing that count casualties in Defective Surface crashes returns the correct number...");
        List<CountResult> crashCount = countWeatherService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(22, result.get().getCasualtyCount().longValue());
    }
}
