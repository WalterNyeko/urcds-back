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
public class CountDistrictServiceTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CountAttributeService countDistrictService;
    private List<Crash> crashes;
    private Log log = LogFactory.getLog(CountDistrictServiceTest.class);

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        log.debug("testing that count crashes returns the correct number of records...");
        List<CountResult> crashCount = countDistrictService.countCrashes(crashes);
        assertEquals(4, crashCount.size());
    }

    @Test
    public void testThatCountKampalaDistrictCrashesReturnsCorrectNumber() {
        log.debug("testing that count Kampala District crashes returns the correct number...");
        List<CountResult> crashCount = countDistrictService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 3).findFirst();
        assertEquals(7, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountLugaziDistrictCrashesReturnsCorrectNumber() {
        log.debug("testing that count Lugazi District crashes returns the correct number...");
        List<CountResult> crashCount = countDistrictService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(3, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountMpigiDistrictCrashesReturnsCorrectNumber() {
        log.debug("testing that count Mpigi District crashes returns the correct number...");
        List<CountResult> crashCount = countDistrictService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 2).findFirst();
        assertEquals(0, result.get().getCrashCount().longValue());
    }

    @Test
    public void testThatCountVehiclesInKampalaDistrictCrashesReturnsCorrectNumber() {
        log.debug("testing that count vehicles in Kampala District crashes returns the correct number...");
        List<CountResult> crashCount = countDistrictService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 3).findFirst();
        assertEquals(19, result.get().getVehicleCount().longValue());
    }

    @Test
    public void testThatCountVehiclesInLugaziDistrictCrashesReturnsCorrectNumber() {
        log.debug("testing that count vehicles in Lugazi District crashes returns the correct number...");
        List<CountResult> crashCount = countDistrictService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(6, result.get().getVehicleCount().longValue());
    }

    @Test
    public void testThatCountCasualtiesInLugaziDistrictCrashesReturnsCorrectNumber() {
        log.debug("testing that count casualties in Lugazi District crashes returns the correct number...");
        List<CountResult> crashCount = countDistrictService.countCrashes(crashes);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(9, result.get().getCasualtyCount().longValue());
    }
}
