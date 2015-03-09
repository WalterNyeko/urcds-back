package com.sweroad.query;

import com.sweroad.model.LabelValue;
import com.sweroad.service.BaseManagerTestCase;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Frank on 3/5/15.
 */
public class CrashSearchTest extends BaseManagerTestCase {

    private CrashSearch crashSearch;

    @Test
    public void testHqlQueryGeneratedForDriverLicenseValid() {
        String expected = "Select c from Crash c join c.vehicles v where v.driver.licenseValid in (:licenseValidList)";
        crashSearch = new CrashSearch();
        crashSearch.setDriverLicenseTypes(new ArrayList<LabelValue>());
        crashSearch.getDriverLicenseTypes().add(new LabelValue());
        crashSearch.getDriverLicenseTypes().get(0).setValue("1");
        assertEquals(expected, crashSearch.toQuery().toString());
    }

    @Test
    public void testHqlQueryGeneratedForDriverLicenseValidityUnknown() {
        String expected = "Select c from Crash c join c.vehicles v where v.driver.licenseValid is NULL";
        crashSearch = new CrashSearch();
        crashSearch.setDriverLicenseTypes(new ArrayList<LabelValue>());
        crashSearch.getDriverLicenseTypes().add(new LabelValue());
        crashSearch.getDriverLicenseTypes().get(0).setValue("-1");
        assertEquals(expected, crashSearch.toQuery().toString());
    }

    @Test
    public void testHqlQueryGeneratedForDriverLicenseValidOrUnknown() {
        String expected = "Select c from Crash c join c.vehicles v where v.driver.licenseValid in (:licenseValidList) or v.driver.licenseValid is NULL";
        crashSearch = new CrashSearch();
        crashSearch.setDriverLicenseTypes(new ArrayList<LabelValue>());
        crashSearch.getDriverLicenseTypes().add(new LabelValue());
        crashSearch.getDriverLicenseTypes().get(0).setValue("1");
        crashSearch.getDriverLicenseTypes().add(new LabelValue());
        crashSearch.getDriverLicenseTypes().get(1).setValue("-1");
        assertEquals(expected, crashSearch.toQuery().toString());
    }
}
