package com.sweroad.query;

import com.sweroad.model.*;
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
    public void testHqlQueryGeneratedForDriverLicense() {
        String expected = "Select DISTINCT c from Crash c join c.vehicles v where v.driver.licenseValid in (:driverLicenseValidList) and c.isRemoved is false";
        crashSearch = new CrashSearch();
        crashSearch.setDriverLicenseTypes(new ArrayList<>());
        crashSearch.getDriverLicenseTypes().add(Quadstate.YES);
        assertEquals(expected, crashSearch.toQuery().toString());
    }

    @Test
    public void testHqlQueryGeneratedForDriverGenderMale() {
        String expected = "Select DISTINCT c from Crash c join c.vehicles v where v.driver.gender in (:driverGenderList) and c.isRemoved is false";
        crashSearch = new CrashSearch();
        crashSearch.setDriverGenders(new ArrayList<>());
        crashSearch.getDriverGenders().add(new LabelValue());
        crashSearch.getDriverGenders().get(0).setValue("M");
        assertEquals(expected, crashSearch.toQuery().toString());
    }

    @Test
    public void testHqlQueryGeneratedForDriverGenderUnknown() {
        String expected = "Select DISTINCT c from Crash c join c.vehicles v where v.driver.gender is NULL and c.isRemoved is false";
        crashSearch = new CrashSearch();
        crashSearch.setDriverGenders(new ArrayList<>());
        crashSearch.getDriverGenders().add(new LabelValue());
        crashSearch.getDriverGenders().get(0).setValue("-1");
        assertEquals(expected, crashSearch.toQuery().toString());
    }

    @Test
    public void testHqlQueryGeneratedForDriverGenderMaleOrUnknown() {
        String expected = "Select DISTINCT c from Crash c join c.vehicles v where v.driver.gender in (:driverGenderList) " +
                "or v.driver.gender is NULL and c.isRemoved is false";
        crashSearch = new CrashSearch();
        crashSearch.setDriverGenders(new ArrayList<>());
        crashSearch.getDriverGenders().add(new LabelValue());
        crashSearch.getDriverGenders().get(0).setValue("M");
        crashSearch.getDriverGenders().add(new LabelValue());
        crashSearch.getDriverGenders().get(1).setValue("-1");
        assertEquals(expected, crashSearch.toQuery().toString());
    }

    @Test
    public void testHqlQueryGeneratedForCasualtyGenderMale() {
        String expected = "Select DISTINCT c from Crash c join c.casualties i where i.gender in (:casualtyGenderList) and c.isRemoved is false";
        crashSearch = new CrashSearch();
        crashSearch.setCasualtyGenders(new ArrayList<>());
        crashSearch.getCasualtyGenders().add(new LabelValue());
        crashSearch.getCasualtyGenders().get(0).setValue("M");
        assertEquals(expected, crashSearch.toQuery().toString());
    }

    @Test
    public void testHqlQueryGeneratedForCasualtyGenderUnknown() {
        String expected = "Select DISTINCT c from Crash c join c.casualties i where i.gender is NULL and c.isRemoved is false";
        crashSearch = new CrashSearch();
        crashSearch.setCasualtyGenders(new ArrayList<>());
        crashSearch.getCasualtyGenders().add(new LabelValue());
        crashSearch.getCasualtyGenders().get(0).setValue("-1");
        assertEquals(expected, crashSearch.toQuery().toString());
    }

    @Test
    public void testHqlQueryGeneratedForCasualtyGenderMaleOrUnknown() {
        String expected = "Select DISTINCT c from Crash c join c.casualties i where i.gender in (:casualtyGenderList) " +
                "or i.gender is NULL and c.isRemoved is false";
        crashSearch = new CrashSearch();
        crashSearch.setCasualtyGenders(new ArrayList<>());
        crashSearch.getCasualtyGenders().add(new LabelValue());
        crashSearch.getCasualtyGenders().get(0).setValue("M");
        crashSearch.getCasualtyGenders().add(new LabelValue());
        crashSearch.getCasualtyGenders().get(1).setValue("-1");
        assertEquals(expected, crashSearch.toQuery().toString());
    }

    @Test
    public void testHqlQueryGeneratedForDriverGenderMaleOrUnknownAndCasualtyMaleOrUnknown() {
        String expected = "Select DISTINCT c from Crash c join c.casualties i join c.vehicles v where i.gender in (:casualtyGenderList) or i.gender is NULL " +
                "and v.driver.gender in (:driverGenderList) or v.driver.gender is NULL and c.isRemoved is false";
        crashSearch = new CrashSearch();
        crashSearch.setDriverGenders(new ArrayList<>());
        crashSearch.getDriverGenders().add(new LabelValue());
        crashSearch.getDriverGenders().get(0).setValue("M");
        crashSearch.getDriverGenders().add(new LabelValue());
        crashSearch.getDriverGenders().get(1).setValue("-1");

        crashSearch.setCasualtyGenders(new ArrayList<>());
        crashSearch.getCasualtyGenders().add(new LabelValue());
        crashSearch.getCasualtyGenders().get(0).setValue("M");
        crashSearch.getCasualtyGenders().add(new LabelValue());
        crashSearch.getCasualtyGenders().get(1).setValue("-1");
        assertEquals(expected, crashSearch.toQuery().toString());
    }

    @Test
    public void testHqlQueryGeneratedForDriverBeltUsed() {
        String expected = "Select DISTINCT c from Crash c join c.vehicles v where v.driver.beltUsed in (:driverBeltUsedList) and c.isRemoved is false";
        crashSearch = new CrashSearch();
        crashSearch.setDriverBeltUsedOptions(new ArrayList<>());
        crashSearch.getDriverBeltUsedOptions().add(Quadstate.YES);
        assertEquals(expected, crashSearch.toQuery().toString());
    }

    @Test
    public void testHqlQueryGeneratedForCasualtyBeltOrHelmetUsed() {
        String expected = "Select DISTINCT c from Crash c join c.casualties i where i.beltOrHelmetUsed in " +
                "(:beltOrHelmetUsedList) and c.isRemoved is false";
        crashSearch = new CrashSearch();
        crashSearch.setCasualtyBeltUsedOptions(new ArrayList<>());
        crashSearch.getCasualtyBeltUsedOptions().add(Quadstate.YES);
        assertEquals(expected, crashSearch.toQuery().toString());
    }

    @Test
    public void testHqlQueryGeneratedForDriverCasualtyType() {
        String expected = "Select DISTINCT c from Crash c join c.vehicles v where v.driver.casualtyType in " +
                "(:driverCasualtyTypeList) and c.isRemoved is false";
        crashSearch = new CrashSearch();
        crashSearch.setDriverCasualtyTypes(new ArrayList<CasualtyType>());
        crashSearch.getDriverCasualtyTypes().add(new CasualtyType());
        crashSearch.getDriverCasualtyTypes().get(0).setId(3L);
        assertEquals(expected, crashSearch.toQuery().toString());
    }

    @Test
    public void testHqlQueryGeneratedForPassengerCasualtyType() {
        String expected = "Select DISTINCT c from Crash c join c.casualties i where i.casualtyType in " +
                "(:casualtyTypeList) and c.isRemoved is false";
        crashSearch = new CrashSearch();
        crashSearch.setCasualtyTypes(new ArrayList<>());
        crashSearch.getCasualtyTypes().add(new CasualtyType());
        crashSearch.getCasualtyTypes().get(0).setId(3L);
        assertEquals(expected, crashSearch.toQuery().toString());
    }

    @Test
    public void testHqlQueryGeneratedForPassengerCasualtyClass() {
        String expected = "Select DISTINCT c from Crash c join c.casualties i where i.casualtyClass in " +
                "(:casualtyClassList) and c.isRemoved is false";
        crashSearch = new CrashSearch();
        crashSearch.setCasualtyClasses(new ArrayList<>());
        crashSearch.getCasualtyClasses().add(new CasualtyClass());
        crashSearch.getCasualtyClasses().get(0).setId(3L);
        assertEquals(expected, crashSearch.toQuery().toString());
    }

    @Test
    public void testHqlQueryGeneratedForDriverCasualtyClass() {
        String expected = "Select DISTINCT c from Crash c join c.vehicles v where v.driver.casualtyType in " +
                "(:driverCasualtyTypeList) and c.isRemoved is false";
        crashSearch = new CrashSearch();
        crashSearch.setDriverCasualtyTypes(new ArrayList<>());
        crashSearch.getDriverCasualtyTypes().add(new CasualtyType());
        crashSearch.getDriverCasualtyTypes().get(0).setId(3L);
        assertEquals(expected, crashSearch.toQuery().toString());
    }

    @Test
    public void testHqlQueryGeneratedForDriverBelow20Years() {
        String expected = "Select DISTINCT c from Crash c join c.vehicles v where v.driver.age <= 19 and c.isRemoved is false";
        crashSearch = new CrashSearch();
        crashSearch.setDriverAgeRanges(new ArrayList<>());
        crashSearch.getDriverAgeRanges().add(new AgeRange(1L, 0, 9));
        crashSearch.getDriverAgeRanges().add(new AgeRange(2L, 10, 19));
        assertEquals(expected, crashSearch.toQuery().toString());
    }

    @Test
    public void testHqlQueryGeneratedForDriverBelow30YrsAndBetween50And59Years() {
        String expected = "Select DISTINCT c from Crash c join c.vehicles v where v.driver.age <= 29 or " +
                "v.driver.age between 50 and 59 and c.isRemoved is false";
        crashSearch = new CrashSearch();
        crashSearch.setDriverAgeRanges(new ArrayList<>());
        crashSearch.getDriverAgeRanges().add(new AgeRange(1L, 0, 9));
        crashSearch.getDriverAgeRanges().add(new AgeRange(2L, 10, 19));
        crashSearch.getDriverAgeRanges().add(new AgeRange(3L, 20, 29));
        crashSearch.getDriverAgeRanges().add(new AgeRange(6L, 50, 59));
        assertEquals(expected, crashSearch.toQuery().toString());
    }

    @Test
    public void testHqlQueryGeneratedForCasualtiesBetween20And59Years() {
        String expected = "Select DISTINCT c from Crash c join c.casualties i where i.age between 20 and 59 and c.isRemoved is false";
        crashSearch = new CrashSearch();
        crashSearch.setCasualtyAgeRanges(new ArrayList<>());
        crashSearch.getCasualtyAgeRanges().add(new AgeRange(3L, 20, 29));
        crashSearch.getCasualtyAgeRanges().add(new AgeRange(4L, 30, 39));
        crashSearch.getCasualtyAgeRanges().add(new AgeRange(5L, 40, 49));
        crashSearch.getCasualtyAgeRanges().add(new AgeRange(6L, 50, 59));
        assertEquals(expected, crashSearch.toQuery().toString());
    }
}