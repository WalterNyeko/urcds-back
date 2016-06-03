package com.sweroad.service.count;

import com.sweroad.Constants;
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
import static org.junit.Assert.assertTrue;

/**
 * Created by Frank on 5/30/16.
 */
public class CountServiceTest extends BaseManagerTestCase {

    @Autowired
    private CountService countService;
    @Autowired
    private CrashService crashService;
    private List<Crash> crashes;
    private Log log = LogFactory.getLog(CountServiceTest.class);

    @Before()
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesReturnsCorrectNumberOfRecords() {
        log.debug("testing that count crashes returns the correct number of records...");
        List<CountResult> crashCount = countService.countCrashes(crashes, Constants.CRASH_SEVERITY);
        assertEquals(5, crashCount.size());
    }

    @Test
    public void testThatCountFatalCrashesReturnsCorrectNumber() {
        log.debug("testing that count fatal crashes returns the correct number...");
        List<CountResult> crashCount = countService.countCrashes(crashes, Constants.CRASH_SEVERITY);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertTrue(result.isPresent() && result.get().getCrashCount() == 2);
    }

}
