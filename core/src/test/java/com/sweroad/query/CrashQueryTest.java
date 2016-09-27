package com.sweroad.query;

import com.sweroad.model.*;
import com.sweroad.service.BaseManagerTestCase;
import com.sweroad.util.DateUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Frank on 12/9/14.
 */
public class CrashQueryTest extends BaseManagerTestCase {

    private CrashQuery crashQuery;

    private List<CrashSeverity> getCrashSeverities() {
        List<CrashSeverity> severities = new ArrayList<CrashSeverity>();
        severities.add(new CrashSeverity());
        severities.add(new CrashSeverity());
        severities.get(0).setId(1L);
        severities.get(1).setId(2L);
        return severities;
    }

    private List<CollisionType> getCollisionTypes() {
        List<CollisionType> collisionTypes = new ArrayList<CollisionType>();
        collisionTypes.add(new CollisionType());
        collisionTypes.add(new CollisionType());
        collisionTypes.get(0).setId(1L);
        collisionTypes.get(1).setId(2L);
        return collisionTypes;
    }

    private List<Weather> getWeatherList() {
        List<Weather> weatherList = new ArrayList<Weather>();
        weatherList.add(new Weather());
        weatherList.add(new Weather());
        weatherList.get(0).setId(1L);
        weatherList.get(1).setId(2L);
        return weatherList;
    }

    private List<VehicleType> getVehicleTypes() {
        List<VehicleType> vehicleTypes = new ArrayList<VehicleType>();
        vehicleTypes.add(new VehicleType());
        vehicleTypes.add(new VehicleType());
        vehicleTypes.get(0).setId(7L);
        vehicleTypes.get(1).setId(10L);
        return vehicleTypes;
    }

    private List<CasualtyClass> getCasualtyClasses() {
        List<CasualtyClass> casualtyClasses = new ArrayList<CasualtyClass>();
        casualtyClasses.add(new CasualtyClass());
        casualtyClasses.get(0).setId(1L);
        return casualtyClasses;
    }

    private List<CasualtyType> getCasualtyTypes() {
        List<CasualtyType> casualtyTypes = new ArrayList<CasualtyType>();
        casualtyTypes.add(new CasualtyType());
        casualtyTypes.get(0).setId(3L);
        return casualtyTypes;
    }

    @Test
    public void testHqlQueryGeneratedForCrashSeverities() {
        String expected = "Select DISTINCT c from Crash c where c.crashSeverity in (:crashSeverityList) and c.isRemoved is false";
        List<CrashSeverity> severities = getCrashSeverities();
        crashQuery = new CrashQuery.CrashQueryBuilder().addQueryable(severities).build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForCrashSeveritiesAndCollisionTypes() {
        String expected = "Select DISTINCT c from Crash c where c.collisionType in (:collisionTypeList) and " +
                "c.crashSeverity in (:crashSeverityList) and c.isRemoved is false";
        List<CrashSeverity> severities = getCrashSeverities();
        List<CollisionType> collisionTypes = getCollisionTypes();
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addQueryable(severities)
                .addQueryable(collisionTypes)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForCrashSeveritiesCollisionTypesAndWeather() {
        String expected = "Select DISTINCT c from Crash c where c.collisionType in (:collisionTypeList) and " +
                "c.crashSeverity in (:crashSeverityList) and " +
                "c.weather in (:weatherList) and c.isRemoved is false";
        List<CrashSeverity> severities = getCrashSeverities();
        List<CollisionType> collisionTypes = getCollisionTypes();
        List<Weather> weatherList = getWeatherList();
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addQueryable(severities)
                .addQueryable(collisionTypes)
                .addQueryable(weatherList)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForCrashSeveritiesAndStartDate() throws Exception {
        String expected = "Select DISTINCT c from Crash c where c.crashSeverity in (:crashSeverityList) and c.crashDateTime >= :startDate" +
                " and c.isRemoved is false";
        List<CrashSeverity> severities = getCrashSeverities();
        Date startDate = DateUtil.convertStringToDate("2014-06-01");
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addQueryable(severities)
                .addStartDate(startDate)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForStartDate() throws Exception {
        String expected = "Select DISTINCT c from Crash c where c.crashDateTime >= :startDate and c.isRemoved is false";
        Date startDate = DateUtil.convertStringToDate("2014-06-01");
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartDate(startDate)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForEndDate() throws Exception {
        String expected = "Select DISTINCT c from Crash c where c.crashDateTime <= :endDate and c.isRemoved is false";
        Date endDate = DateUtil.convertStringToDate("2014-12-31");
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addEndDate(endDate)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForDateRange() throws Exception {
        String expected = "Select DISTINCT c from Crash c where c.crashDateTime between :startDate and :endDate and c.isRemoved is false";
        Date startDate = DateUtil.convertStringToDate("2014-06-01");
        Date endDate = DateUtil.convertStringToDate("2014-12-31");
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartDate(startDate)
                .addEndDate(endDate)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForStartMonth() throws Exception {
        String expected = "Select DISTINCT c from Crash c where month(c.crashDateTime) >= :startMonth and c.isRemoved is false";
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartMonth(6)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForEndMonth() throws Exception {
        String expected = "Select DISTINCT c from Crash c where month(c.crashDateTime) <= :endMonth and c.isRemoved is false";
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addEndMonth(12)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForMonthRange() throws Exception {
        String expected = "Select DISTINCT c from Crash c where (month(c.crashDateTime) between :startMonth and :endMonth) and c.isRemoved is false";
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartMonth(6)
                .addEndMonth(12)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForStartHour() {
        String expected = "Select DISTINCT c from Crash c where hour(c.crashDateTime) >= :startHour and c.isRemoved is false";
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartHour(10)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForEndHour() {
        String expected = "Select DISTINCT c from Crash c where hour(c.crashDateTime) <= :endHour and c.isRemoved is false";
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addEndHour(10)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForHourRange() {
        String expected = "Select DISTINCT c from Crash c where hour(c.crashDateTime) between :startHour and :endHour and c.isRemoved is false";
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartHour(8)
                .addEndHour(17)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForCrashSeveritiesAndHourRange() {
        String expected = "Select DISTINCT c from Crash c where c.crashSeverity in (:crashSeverityList) and " +
                "hour(c.crashDateTime) between :startHour and :endHour and c.isRemoved is false";
        List<CrashSeverity> severities = getCrashSeverities();
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addQueryable(severities)
                .addStartHour(8)
                .addEndHour(17)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForCrashesInvolvingMediumOmnibusesAndMotorcycles() {
        String expected = "Select DISTINCT c from Crash c join c.vehicles v where v.vehicleType in (:vehicleTypeList) and c.isRemoved is false";
        List<VehicleType> vehicleTypes = getVehicleTypes();
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addQueryable(vehicleTypes)
                .joinVehicles(true)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForCrashesInvolvingPedestrians() {
        String expected = "Select DISTINCT c from Crash c join c.casualties i where i.casualtyClass in (:casualtyClassList) and c.isRemoved is false";
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addQueryable(getCasualtyClasses())
                .joinCasualties(true)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForCrashesInvolvingSlightlyInjuredPedestrians() {
        String expected = "Select DISTINCT c from Crash c join c.casualties i where i.casualtyClass in (:casualtyClassList) and " +
                "i.casualtyType in (:casualtyTypeList) and c.isRemoved is false";
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addQueryable(getCasualtyClasses())
                .addQueryable(getCasualtyTypes())
                .joinCasualties(true)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForCrashesInvolvingFemaleDriversAbove30Years() {
        String expected = "Select DISTINCT c from Crash c join c.vehicles v where v.driver.age > :age and v.driver.gender = :gender and c.isRemoved is false";
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
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .joinVehicles(true)
                .addCustomQueryable(customQueryableGreaterThan30)
                .addCustomQueryable(customQueryableFemaleDriver)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForCrashesInvolvingCasualtiesBetween10And20Years() {
        String expected = "Select DISTINCT c from Crash c join c.casualties i where i.age <= :age2 and i.age >= :age1 and c.isRemoved is false";
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
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .joinCasualties(true)
                .addCustomQueryable(customQueryableGreaterThanEqualTo10)
                .addCustomQueryable(customQueryableLessThanEqualTo20)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForCrashesInvolvingFemaleDriversAbove30YearsAndPedestrians() {
        String expected = "Select DISTINCT c from Crash c join c.casualties i join c.vehicles v " +
                "where i.casualtyClass in (:casualtyClassList) and v.driver.age > :age and v.driver.gender = :gender and c.isRemoved is false";
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
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .joinVehicles(true)
                .addCustomQueryable(customQueryableGreaterThan30)
                .addCustomQueryable(customQueryableFemaleDriver)
                .addQueryable(getCasualtyClasses())
                .joinCasualties(true)
                .build();
        assertEquals(expected, crashQuery.toString());
    }
}