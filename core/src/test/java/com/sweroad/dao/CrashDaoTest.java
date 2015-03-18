package com.sweroad.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.sweroad.model.*;
import com.sweroad.query.Comparison;
import com.sweroad.query.CrashQuery;
import com.sweroad.query.CrashSearch;
import com.sweroad.query.CustomQueryable;
import com.sweroad.service.GenericManager;
import com.sweroad.util.DateUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrashDaoTest extends BaseDaoTestCase {

    @Autowired
    private CrashDao crashDao;
    @Autowired
    private GenericManager<VehicleType, Long> vehicleTypeManager;
    @Autowired
    private GenericManager<CasualtyType, Long> casualtyTypeManager;
    @Autowired
    private GenericManager<CasualtyClass, Long> casualtyClassManager;

    private List<CrashSeverity> severities;
    private List<CollisionType> collisionTypes;
    private List<Weather> weathers;
    private List<VehicleType> vehicleTypes;

    @Test
    public void testThatFindCrashByTaNoWorks() {
        Crash crash = crashDao.findByTarNo("A1509/LGZ");
        assertNotNull(crash);
    }

    @Test
    public void testFindFatalCrashesByQueryCrash() {
        severities = new ArrayList<CrashSeverity>();
        severities.add(new CrashSeverity());
        severities.get(0).setId(1L);
        CrashQuery query = new CrashQuery.CrashQueryBuilder()
                .addQueryable(severities).build();
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(query);
        assertEquals(2, crashes.size());
    }

    @Test
    public void testFindFatalAndSeriousCrashesByQueryCrash() {
        severities = new ArrayList<CrashSeverity>();
        severities.add(new CrashSeverity());
        severities.add(new CrashSeverity());
        severities.get(0).setId(1L);
        severities.get(1).setId(2L);
        CrashQuery query = new CrashQuery.CrashQueryBuilder()
                .addQueryable(severities).build();
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(query);
        assertEquals(6, crashes.size());
    }

    @Test
    public void testFindAngleCrashesOnARainyDay() {
        collisionTypes = new ArrayList<CollisionType>();
        weathers = new ArrayList<Weather>();
        collisionTypes.add(new CollisionType());
        weathers.add(new Weather());
        collisionTypes.get(0).setId(6L);
        weathers.get(0).setId(2L);
        CrashQuery crashQuery = new CrashQuery.CrashQueryBuilder()
                .addQueryable(collisionTypes)
                .addQueryable(weathers)
                .build();
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashQuery);
        assertEquals(3, crashes.size());
    }

    @Test
    public void testFindCrashesBetweenDates() throws Exception {
        Date startDate = DateUtil.convertStringToDate("18/02/2014");
        Date endDate = DateUtil.convertStringToDate("18/06/2014");
        CrashQuery crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartDate(startDate)
                .addEndDate(endDate)
                .build();
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashQuery);
        assertEquals(7, crashes.size());
    }

    @Test
    public void testFindCrashesFromDate() throws Exception {
        Date startDate = DateUtil.convertStringToDate("02/06/2014");
        CrashQuery crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartDate(startDate)
                .build();
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashQuery);
        assertEquals(6, crashes.size());
    }

    @Test
    public void testFindCrashesBetweenMonths() throws Exception {
        Date startDate = DateUtil.convertStringToDate("01/12/2013");
        Date endDate = DateUtil.convertStringToDate("01/05/2014");
        CrashQuery crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartDate(startDate)
                .addEndDate(endDate)
                .setUseMonth(true)
                .build();
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashQuery);
        assertEquals(3, crashes.size());
    }

    @Test
    public void testFindCrashesUpToMonth() throws Exception {
        Date endDate = DateUtil.convertStringToDate("01/05/2014");
        CrashQuery crashQuery = new CrashQuery.CrashQueryBuilder()
                .addEndDate(endDate)
                .setUseMonth(true)
                .build();
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashQuery);
        assertEquals(4, crashes.size());
    }

    @Test
    public void testFindCrashesBetweenYears() throws Exception {
        Date startDate = DateUtil.convertStringToDate("01/12/2012");
        Date endDate = DateUtil.convertStringToDate("01/12/2013");
        CrashQuery crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartDate(startDate)
                .addEndDate(endDate)
                .setUseYear(true)
                .build();
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashQuery);
        assertEquals(2, crashes.size());
    }

    @Test
    public void testFindCrashesUpToYear() throws Exception {
        Date endDate = DateUtil.convertStringToDate("01/12/2013");
        CrashQuery crashQuery = new CrashQuery.CrashQueryBuilder()
                .addEndDate(endDate)
                .setUseYear(true)
                .build();
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashQuery);
        assertEquals(2, crashes.size());
    }

    @Test
    public void testFindCrashesThatOccurredBetween16And20Hours() {
        CrashQuery crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartHour(16)
                .addEndHour(20)
                .build();
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashQuery);
        assertEquals(4, crashes.size());
    }

    @Test
    public void testFindCrashesThatOccurredBetween08And10HoursBeforeJune2014() throws Exception {
        Date endDate = DateUtil.convertStringToDate("01/05/2014");
        CrashQuery crashQuery = new CrashQuery.CrashQueryBuilder()
                .addEndDate(endDate)
                .setUseMonth(true)
                .addStartHour(8)
                .addEndHour(10)
                .build();
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashQuery);
        assertEquals(2, crashes.size());
    }

    @Test
    public void testFindAllCrashesThatOccurredFromMidday() {
        CrashQuery crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartHour(12)
                .build();
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashQuery);
        assertEquals(4, crashes.size());
    }

    @Test
    public void testFindAllCrashesThatOccurredFromMiddayIn2014() throws Exception {
        Date startDate = DateUtil.convertStringToDate("01/01/2014");
        Date endDate = DateUtil.convertStringToDate("01/12/2014");
        CrashQuery crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartDate(startDate)
                .addEndDate(endDate)
                .setUseYear(true)
                .addStartHour(12)
                .build();
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashQuery);
        assertEquals(3, crashes.size());
    }

    @Test
    public void testFindAllCrashesThatOccurredUpToMiddayIn2014() throws Exception {
        Date startDate = DateUtil.convertStringToDate("01/01/2014");
        Date endDate = DateUtil.convertStringToDate("01/12/2014");
        CrashQuery crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartDate(startDate)
                .addEndDate(endDate)
                .setUseYear(true)
                .addEndHour(12)
                .build();
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashQuery);
        assertEquals(5, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingMediumOmnibusesAndMotorcycles() {
        vehicleTypes = new ArrayList<VehicleType>();
        vehicleTypes.add(vehicleTypeManager.get(7L));
        vehicleTypes.add(vehicleTypeManager.get(10L));
        CrashQuery crashQuery = new CrashQuery.CrashQueryBuilder()
                .addQueryable(vehicleTypes)
                .joinVehicles(true)
                .build();
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashQuery);
        assertEquals(4, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingPedestrians() {
        List<CasualtyClass> casualtyClasses = new ArrayList<CasualtyClass>();
        casualtyClasses.add(casualtyClassManager.get(1L));
        CrashQuery crashQuery = new CrashQuery.CrashQueryBuilder()
                .addQueryable(casualtyClasses)
                .joinCasualties(true)
                .build();
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashQuery);
        assertEquals(6, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingFemaleDriversAbove30Years() {
        CustomQueryable customQueryableGreaterThan30 = new CustomQueryable.CustomQueryableBuilder()
                .addCrashJoinType(CrashQuery.CrashQueryBuilder.CrashJoinType.VEHICLE)
                .addProperty("driver.age")
                .addComparison(Comparison.GT)
                .addParameterName("age")
                .addParameterValue(30)
                .shouldEncloseInParenthesis(false)
                .build();
        CustomQueryable customQueryableFemaleDriver = new CustomQueryable.CustomQueryableBuilder()
                .addCrashJoinType(CrashQuery.CrashQueryBuilder.CrashJoinType.VEHICLE)
                .addProperty("driver.gender")
                .addComparison(Comparison.EQ)
                .addParameterName("gender")
                .addParameterValue("F")
                .shouldEncloseInParenthesis(false)
                .build();
        CrashQuery crashQuery = new CrashQuery.CrashQueryBuilder()
                .joinVehicles(true)
                .addCustomQueryable(customQueryableGreaterThan30)
                .addCustomQueryable(customQueryableFemaleDriver)
                .build();
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashQuery);
        assertEquals(3, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingCasualtiesBetweenAge10And20() {
        CustomQueryable customQueryableGreaterThanEqualTo10 = new CustomQueryable.CustomQueryableBuilder()
                .addCrashJoinType(CrashQuery.CrashQueryBuilder.CrashJoinType.CASUALTY)
                .addProperty("age")
                .addComparison(Comparison.GTE)
                .addParameterName("age1")
                .addParameterValue(10)
                .shouldEncloseInParenthesis(false)
                .build();
        CustomQueryable customQueryableLessThanEqualTo20 = new CustomQueryable.CustomQueryableBuilder()
                .addCrashJoinType(CrashQuery.CrashQueryBuilder.CrashJoinType.CASUALTY)
                .addProperty("age")
                .addComparison(Comparison.LTE)
                .addParameterName("age2")
                .addParameterValue(20)
                .shouldEncloseInParenthesis(false)
                .build();
        CrashQuery crashQuery = new CrashQuery.CrashQueryBuilder()
                .joinCasualties(true)
                .addCustomQueryable(customQueryableGreaterThanEqualTo10)
                .addCustomQueryable(customQueryableLessThanEqualTo20)
                .build();
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashQuery);
        assertEquals(3, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingFemaleCasualtiesBetweenAge10And20() {
        CustomQueryable customQueryableGreaterThanEqualTo10 = new CustomQueryable.CustomQueryableBuilder()
                .addCrashJoinType(CrashQuery.CrashQueryBuilder.CrashJoinType.CASUALTY)
                .addProperty("age")
                .addComparison(Comparison.GTE)
                .addParameterName("age1")
                .addParameterValue(10)
                .shouldEncloseInParenthesis(false)
                .build();
        CustomQueryable customQueryableLessThanEqualTo20 = new CustomQueryable.CustomQueryableBuilder()
                .addCrashJoinType(CrashQuery.CrashQueryBuilder.CrashJoinType.CASUALTY)
                .addProperty("age")
                .addComparison(Comparison.LTE)
                .addParameterName("age2")
                .addParameterValue(20)
                .shouldEncloseInParenthesis(false)
                .build();
        CustomQueryable customQueryableFemaleDriver = new CustomQueryable.CustomQueryableBuilder()
                .addCrashJoinType(CrashQuery.CrashQueryBuilder.CrashJoinType.CASUALTY)
                .addProperty("gender")
                .addComparison(Comparison.EQ)
                .addParameterName("gender")
                .addParameterValue("F")
                .shouldEncloseInParenthesis(false)
                .build();
        CrashQuery crashQuery = new CrashQuery.CrashQueryBuilder()
                .joinCasualties(true)
                .addCustomQueryable(customQueryableGreaterThanEqualTo10)
                .addCustomQueryable(customQueryableLessThanEqualTo20)
                .addCustomQueryable(customQueryableFemaleDriver)
                .build();
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashQuery);
        assertEquals(1, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingFemaleDriversAbove30YearsAndPedestrians() {
        List<CasualtyClass> casualtyClasses = new ArrayList<CasualtyClass>();
        casualtyClasses.add(casualtyClassManager.get(1L));
        CustomQueryable customQueryableGreaterThan30 = new CustomQueryable.CustomQueryableBuilder()
                .addCrashJoinType(CrashQuery.CrashQueryBuilder.CrashJoinType.VEHICLE)
                .addProperty("driver.age")
                .addComparison(Comparison.GT)
                .addParameterName("age")
                .addParameterValue(30)
                .shouldEncloseInParenthesis(false)
                .build();
        CustomQueryable customQueryableFemaleDriver = new CustomQueryable.CustomQueryableBuilder()
                .addCrashJoinType(CrashQuery.CrashQueryBuilder.CrashJoinType.VEHICLE)
                .addProperty("driver.gender")
                .addComparison(Comparison.EQ)
                .addParameterName("gender")
                .addParameterValue("F")
                .shouldEncloseInParenthesis(false)
                .build();
        CrashQuery crashQuery = new CrashQuery.CrashQueryBuilder()
                .joinVehicles(true)
                .addCustomQueryable(customQueryableGreaterThan30)
                .addCustomQueryable(customQueryableFemaleDriver)
                .addQueryable(casualtyClasses)
                .joinCasualties(true)
                .build();
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashQuery);
        assertEquals(3, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingMotorVehiclesWithDriversHavingValidLicense() {
        List<LabelValue> driverLicenseTypes = new ArrayList<LabelValue>();
        driverLicenseTypes.add(new LabelValue());
        driverLicenseTypes.get(0).setValue("1");
        CrashSearch crashSearch = new CrashSearch();
        crashSearch.setDriverLicenseTypes(driverLicenseTypes);
        vehicleTypes = new ArrayList<VehicleType>();
        vehicleTypes.add(vehicleTypeManager.get(1L));
        crashSearch.setVehicleTypes(vehicleTypes);
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashSearch.toQuery());
        assertEquals(8, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingMotorVehiclesWithDriversHavingInvalidLicense() {
        List<LabelValue> driverLicenseTypes = new ArrayList<LabelValue>();
        driverLicenseTypes.add(new LabelValue());
        driverLicenseTypes.get(0).setValue("0");
        CrashSearch crashSearch = new CrashSearch();
        crashSearch.setDriverLicenseTypes(driverLicenseTypes);
        vehicleTypes = new ArrayList<VehicleType>();
        vehicleTypes.add(vehicleTypeManager.get(1L));
        crashSearch.setVehicleTypes(vehicleTypes);
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashSearch.toQuery());
        assertEquals(2, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingMotorVehiclesWithDriversHavingValidOrInvalidLicense() {
        List<LabelValue> driverLicenseTypes = new ArrayList<LabelValue>();
        driverLicenseTypes.add(new LabelValue());
        driverLicenseTypes.get(0).setValue("1");
        driverLicenseTypes.add(new LabelValue());
        driverLicenseTypes.get(1).setValue("0");
        CrashSearch crashSearch = new CrashSearch();
        crashSearch.setDriverLicenseTypes(driverLicenseTypes);
        vehicleTypes = new ArrayList<VehicleType>();
        vehicleTypes.add(vehicleTypeManager.get(1L));
        crashSearch.setVehicleTypes(vehicleTypes);
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashSearch.toQuery());
        assertEquals(10, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingMotorVehiclesWithDriversHavingNullLicenseType() {
        List<LabelValue> driverLicenseTypes = new ArrayList<LabelValue>();
        driverLicenseTypes.add(new LabelValue());
        driverLicenseTypes.get(0).setValue("-1");
        CrashSearch crashSearch = new CrashSearch();
        crashSearch.setDriverLicenseTypes(driverLicenseTypes);
        vehicleTypes = new ArrayList<VehicleType>();
        vehicleTypes.add(vehicleTypeManager.get(1L));
        crashSearch.setVehicleTypes(vehicleTypes);
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashSearch.toQuery());
        assertEquals(1, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingMotorVehiclesWithDriversHavingValidOrNullLicense() {
        List<LabelValue> driverLicenseTypes = new ArrayList<LabelValue>();
        driverLicenseTypes.add(new LabelValue());
        driverLicenseTypes.get(0).setValue("1");
        driverLicenseTypes.add(new LabelValue());
        driverLicenseTypes.get(1).setValue("-1");
        CrashSearch crashSearch = new CrashSearch();
        crashSearch.setDriverLicenseTypes(driverLicenseTypes);
        vehicleTypes = new ArrayList<VehicleType>();
        vehicleTypes.add(vehicleTypeManager.get(1L));
        crashSearch.setVehicleTypes(vehicleTypes);
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashSearch.toQuery());
        assertEquals(9, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingFemaleDrivers() {
        List<LabelValue> driverGenders = new ArrayList<LabelValue>();
        driverGenders.add(new LabelValue());
        driverGenders.get(0).setValue("F");
        CrashSearch crashSearch = new CrashSearch();
        crashSearch.setDriverGenders(driverGenders);
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashSearch.toQuery());
        assertEquals(6, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingMaleDrivers() {
        List<LabelValue> driverGenders = new ArrayList<LabelValue>();
        driverGenders.add(new LabelValue());
        driverGenders.get(0).setValue("M");
        CrashSearch crashSearch = new CrashSearch();
        crashSearch.setDriverGenders(driverGenders);
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashSearch.toQuery());
        assertEquals(9, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingDriversWithNullGender() {
        List<LabelValue> driverGenders = new ArrayList<LabelValue>();
        driverGenders.add(new LabelValue());
        driverGenders.get(0).setValue("-1");
        CrashSearch crashSearch = new CrashSearch();
        crashSearch.setDriverGenders(driverGenders);
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashSearch.toQuery());
        assertEquals(1, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingFemaleOrDriversWithNullGender() {
        List<LabelValue> driverGenders = new ArrayList<LabelValue>();
        driverGenders.add(new LabelValue());
        driverGenders.get(0).setValue("F");
        driverGenders.add(new LabelValue());
        driverGenders.get(1).setValue("-1");
        CrashSearch crashSearch = new CrashSearch();
        crashSearch.setDriverGenders(driverGenders);
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashSearch.toQuery());
        assertEquals(7, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingFemaleDriversAndFemaleOrCasualtiesWithUnknownGender() {
        CrashSearch crashSearch = new CrashSearch();
        crashSearch.setDriverGenders(new ArrayList<LabelValue>());
        crashSearch.getDriverGenders().add(new LabelValue());
        crashSearch.getDriverGenders().get(0).setValue("F");

        crashSearch.setCasualtyGenders(new ArrayList<LabelValue>());
        crashSearch.getCasualtyGenders().add(new LabelValue());
        crashSearch.getCasualtyGenders().get(0).setValue("F");
        crashSearch.getCasualtyGenders().add(new LabelValue());
        crashSearch.getCasualtyGenders().get(1).setValue("-1");
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashSearch.toQuery());
        assertEquals(6, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingDriversNotWearingBelts() {
        CrashSearch crashSearch = new CrashSearch();
        crashSearch.setDriverBeltUsedOptions(new ArrayList<LabelValue>());
        crashSearch.getDriverBeltUsedOptions().add(new LabelValue());
        crashSearch.getDriverBeltUsedOptions().get(0).setValue("0");
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashSearch.toQuery());
        assertEquals(9, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingDriversWearingBelts() {
        CrashSearch crashSearch = new CrashSearch();
        crashSearch.setDriverBeltUsedOptions(new ArrayList<LabelValue>());
        crashSearch.getDriverBeltUsedOptions().add(new LabelValue());
        crashSearch.getDriverBeltUsedOptions().get(0).setValue("1");
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashSearch.toQuery());
        assertEquals(8, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingDriversWearingBeltsUnknown() {
        CrashSearch crashSearch = new CrashSearch();
        crashSearch.setDriverBeltUsedOptions(new ArrayList<LabelValue>());
        crashSearch.getDriverBeltUsedOptions().add(new LabelValue());
        crashSearch.getDriverBeltUsedOptions().get(0).setValue("-1");
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashSearch.toQuery());
        assertEquals(1, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingCasualtiesNotWearingBelts() {
        CrashSearch crashSearch = new CrashSearch();
        crashSearch.setCasualtyBeltUsedOptions(new ArrayList<LabelValue>());
        crashSearch.getCasualtyBeltUsedOptions().add(new LabelValue());
        crashSearch.getCasualtyBeltUsedOptions().get(0).setValue("0");
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashSearch.toQuery());
        assertEquals(5, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingCasualtiesWearingBelts() {
        CrashSearch crashSearch = new CrashSearch();
        crashSearch.setCasualtyBeltUsedOptions(new ArrayList<LabelValue>());
        crashSearch.getCasualtyBeltUsedOptions().add(new LabelValue());
        crashSearch.getCasualtyBeltUsedOptions().get(0).setValue("1");
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashSearch.toQuery());
        assertEquals(5, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingCasualtiesWearingBeltsUnknown() {
        CrashSearch crashSearch = new CrashSearch();
        crashSearch.setCasualtyBeltUsedOptions(new ArrayList<LabelValue>());
        crashSearch.getCasualtyBeltUsedOptions().add(new LabelValue());
        crashSearch.getCasualtyBeltUsedOptions().get(0).setValue("-1");
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashSearch.toQuery());
        assertEquals(1, crashes.size());
    }

    @Test
    public void testFindAllCrashesInvolvingSeriouslyInjuredPedestrians() {
        CrashSearch crashSearch = new CrashSearch();
        crashSearch.setCasualtyTypes(new ArrayList<CasualtyType>());
        crashSearch.getCasualtyTypes().add(casualtyTypeManager.get(2L));
        crashSearch.setCasualtyClasses(new ArrayList<CasualtyClass>());
        crashSearch.getCasualtyClasses().add(casualtyClassManager.get(1L));
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashSearch.toQuery());
        assertEquals(2, crashes.size());
    }

    @Test
    public void testHqlQueryGeneratedForDriverBelow20Years() {
        CrashSearch crashSearch = new CrashSearch();
        crashSearch = new CrashSearch();
        crashSearch.setDriverAgeRanges(new ArrayList<LabelValue>());
        crashSearch.getDriverAgeRanges().add(new AgeRange(1L, 0, 9));
        crashSearch.getDriverAgeRanges().add(new AgeRange(2L, 10, 19));
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashSearch.toQuery());
        assertEquals(5, crashes.size());
    }

    @Test
    public void testHqlQueryGeneratedForDriverBelow30YrsAndBetween50And59Years() {
        CrashSearch crashSearch = new CrashSearch();
        crashSearch = new CrashSearch();
        crashSearch.setDriverAgeRanges(new ArrayList<LabelValue>());
        crashSearch.getDriverAgeRanges().add(new AgeRange(1L, 0, 9));
        crashSearch.getDriverAgeRanges().add(new AgeRange(2L, 10, 19));
        crashSearch.getDriverAgeRanges().add(new AgeRange(3L, 20, 29));
        crashSearch.getDriverAgeRanges().add(new AgeRange(6L, 50, 59));
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashSearch.toQuery());
        assertEquals(7, crashes.size());
    }

    @Test
    public void testHqlQueryGeneratedForCasualtiesBetween20And59Years() {
        CrashSearch crashSearch = new CrashSearch();
        crashSearch = new CrashSearch();
        crashSearch.setCasualtyAgeRanges(new ArrayList<LabelValue>());
        crashSearch.getCasualtyAgeRanges().add(new AgeRange(3L, 20, 29));
        crashSearch.getCasualtyAgeRanges().add(new AgeRange(4L, 30, 39));
        crashSearch.getCasualtyAgeRanges().add(new AgeRange(5L, 40, 49));
        crashSearch.getCasualtyAgeRanges().add(new AgeRange(6L, 50, 59));
        List<Crash> crashes = crashDao.findCrashesByQueryCrash(crashSearch.toQuery());
        assertEquals(5, crashes.size());
    }
}