package com.sweroad.query;

import com.sweroad.model.LabelValue;
import com.sweroad.service.BaseManagerTestCase;
import com.sweroad.service.LookupManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Frank on 3/5/15.
 */
public class CrashSearchTest extends BaseManagerTestCase {

    @Autowired
    private LookupManager lookupManager;
    private CrashSearch crashSearch;

    @Test
    public void testHqlQueryGeneratedForDriverLicenseValid() {
        String expected = "Select c from Crash c join c.vehicles v where v.driver.licenseValid in (1)";
        crashSearch = new CrashSearch(lookupManager);
        crashSearch.setDriverLicenseTypes(new ArrayList<LabelValue>());
        crashSearch.getDriverLicenseTypes().add(new LabelValue());
        crashSearch.getDriverLicenseTypes().get(0).setValue("1");
        assertEquals(expected, crashSearch.toQuery().toString());
    }

//    @Test
//    public void testHqlQueryGeneratedForDriverLicenseValidityUnknown() {
//        String expected = "Select c from Crash c join c.vehicles v where v.driver.licenseValid is null";
//        crashSearch = new CrashSearch(lookupManager);
//        crashSearch.setDriverLicenseTypes(new ArrayList<LabelValue>());
//        crashSearch.getDriverLicenseTypes().add(new LabelValue());
//        crashSearch.getDriverLicenseTypes().get(0).setValue("1");
//        assertEquals(expected, crashSearch.toQuery().toString());
//    }
}
