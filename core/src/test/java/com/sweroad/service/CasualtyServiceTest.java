package com.sweroad.service;

import com.sweroad.model.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Created by Frank on 2/19/15.
 */
public class CasualtyServiceTest extends BaseManagerTestCase {
    @Autowired
    private CasualtyService casualtyService;
    @Autowired
    private CrashService crashService;

    @Test
    public void testExtractCasualtiesFromCrashList() {
        List<Crash> crashes = crashService.getAllDistinct();
        assertEquals(22, casualtyService.extractCasualtiesFromCrashList(crashes).size());
    }

    @Test
    public void testThatAllCasualtiesExtractedHaveNonNullCrash() {
        List<Crash> crashes = crashService.getAllDistinct();
        List<Casualty> casualties = casualtyService.extractCasualtiesFromCrashList(crashes);
        boolean nullCrashExists = false;
        for (Casualty casualty : casualties) {
            if (casualty.getCrash() == null) {
                nullCrashExists = true;
                break;
            }
        }
        assertFalse(nullCrashExists);
    }
}
