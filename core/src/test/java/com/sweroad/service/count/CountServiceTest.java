package com.sweroad.service.count;

import com.sweroad.model.Crash;
import com.sweroad.service.BaseManagerTestCase;
import com.sweroad.service.CrashService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
    }

    @Test
    public void testThatCountFatalCrashesReturnsCorrectNumber() {
        log.debug("testing that count fatal crashes returns the correct number...");
    }

}
