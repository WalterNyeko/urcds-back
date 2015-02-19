package com.sweroad.service;

import com.sweroad.model.Casualty;
import com.sweroad.model.Crash;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Created by Frank on 2/19/15.
 */
public class CasualtyManagerTest extends BaseManagerTestCase {
    @Autowired
    private CasualtyManager casualtyManager;
    @Autowired
    private CrashManager crashManager;

    @Test
    public void testExtractCasualtiesFromCrashList() {
        List<Crash> crashes = crashManager.getAllDistinct();
        assertEquals(22, casualtyManager.extractCasualtiesFromCrashList(crashes).size());
    }

    @Test
    public void testThatAllCasualtiesExtractedHaveNonNullCrash() {
        List<Crash> crashes = crashManager.getAllDistinct();
        List<Casualty> casualties = casualtyManager.extractCasualtiesFromCrashList(crashes);
        boolean nullCrashExists = false;
        for(Casualty casualty : casualties) {
            if(casualty.getCrash() == null) {
                nullCrashExists = true;
                break;
            }
        }
        assertFalse(nullCrashExists);
    }
}
