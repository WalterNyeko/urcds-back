package com.sweroad.service.count;

import com.sweroad.model.AttributeType;
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
 * Created by Frank on 5/30/16.
 */
public class CountServiceTest extends BaseManagerTestCase {

    @Autowired
    private CountService countService;
    @Autowired
    private CrashService crashService;
    private List<Crash> crashes;

    @Before
    public void setUp() {
        crashes = crashService.getAll();
    }

    @Test
    public void testThatCountCrashesBySeverityReturnsCorrectNumberOfRecords() {
        assertEquals(6, countService.countCrashes(crashes, AttributeType.CRASH_SEVERITY).size());
    }

    @Test
    public void testThatCountFatalCrashesReturnsCorrectNumber() {
        List<CountResult> crashCount = countService.countCrashes(crashes, AttributeType.CRASH_SEVERITY);
        Optional<CountResult> result = crashCount.stream().filter(c -> c.getAttribute().getId() == 1).findFirst();
        assertEquals(2, result.get().getCrashCount());
    }

}
