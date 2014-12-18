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

    @Test
    public void testHqlQueryGeneratedForCrashSeverities() {
        String expected = "Select c from Crash c where c.crashSeverity in (:crashSeverityList)";
        List<CrashSeverity> severities = getCrashSeverities();
        crashQuery = new CrashQuery.CrashQueryBuilder().addQueryable(severities).build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForCrashSeveritiesAndCollisionTypes() {
        String expected = "Select c from Crash c where c.collisionType in (:collisionTypeList) and " +
                "c.crashSeverity in (:crashSeverityList)";
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
        String expected = "Select c from Crash c where c.collisionType in (:collisionTypeList) and " +
                "c.crashSeverity in (:crashSeverityList) and " +
                "c.weather in (:weatherList)";
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
        String expected = "Select c from Crash c where c.crashSeverity in (:crashSeverityList) and c.crashDateTime >= :startDate";
        List<CrashSeverity> severities = getCrashSeverities();
        Date startDate = DateUtil.convertStringToDate("01/06/2014");
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addQueryable(severities)
                .addStartDate(startDate)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForStartDate() throws Exception {
        String expected = "Select c from Crash c where c.crashDateTime >= :startDate";
        Date startDate = DateUtil.convertStringToDate("01/06/2014");
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartDate(startDate)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForEndDate() throws Exception {
        String expected = "Select c from Crash c where c.crashDateTime <= :endDate";
        Date endDate = DateUtil.convertStringToDate("31/12/2014");
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addEndDate(endDate)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForDateRange() throws Exception {
        String expected = "Select c from Crash c where c.crashDateTime between :startDate and :endDate";
        Date startDate = DateUtil.convertStringToDate("01/06/2014");
        Date endDate = DateUtil.convertStringToDate("31/12/2014");
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartDate(startDate)
                .addEndDate(endDate)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForStartMonth() throws Exception {
        String expected = "Select c from Crash c where ((month(c.crashDateTime) >= month(:startDate) and " +
                "year(c.crashDateTime) = year(:startDate)) or year(c.crashDateTime) > year(:startDate))";
        Date startDate = DateUtil.convertStringToDate("01/06/2014");
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartDate(startDate)
                .setUseMonth(true)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForEndMonth() throws Exception {
        String expected = "Select c from Crash c where ((month(c.crashDateTime) <= month(:endDate) and " +
                "year(c.crashDateTime) = year(:endDate)) or year(c.crashDateTime) < year(:endDate))";
        Date endDate = DateUtil.convertStringToDate("31/12/2014");
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addEndDate(endDate)
                .setUseMonth(true)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForMonthRange() throws Exception {
        String expected = "Select c from Crash c where (((month(c.crashDateTime) >= month(:startDate) and " +
                "year(c.crashDateTime) = year(:startDate)) or year(c.crashDateTime) > year(:startDate))) " +
                "and (((month(c.crashDateTime) <= month(:endDate) and " +
                "year(c.crashDateTime) = year(:endDate)) or year(c.crashDateTime) < year(:endDate)))";
        Date startDate = DateUtil.convertStringToDate("01/06/2014");
        Date endDate = DateUtil.convertStringToDate("31/12/2014");
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartDate(startDate)
                .addEndDate(endDate)
                .setUseMonth(true)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForStartYear() throws Exception {
        String expected = "Select c from Crash c where year(c.crashDateTime) >= year(:startDate)";
        Date startDate = DateUtil.convertStringToDate("01/06/2014");
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartDate(startDate)
                .setUseYear(true)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForEndYear() throws Exception {
        String expected = "Select c from Crash c where year(c.crashDateTime) <= year(:endDate)";
        Date endDate = DateUtil.convertStringToDate("31/12/2014");
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addEndDate(endDate)
                .setUseYear(true)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForYearRange() throws Exception {
        String expected = "Select c from Crash c where year(c.crashDateTime) between year(:startDate) and year(:endDate)";
        Date startDate = DateUtil.convertStringToDate("01/06/2014");
        Date endDate = DateUtil.convertStringToDate("31/12/2014");
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartDate(startDate)
                .addEndDate(endDate)
                .setUseYear(true)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForStartHour() {
        String expected = "Select c from Crash c where hour(c.crashDateTime) >= :startHour";
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartHour(10)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForEndHour() {
        String expected = "Select c from Crash c where hour(c.crashDateTime) <= :endHour";
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addEndHour(10)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForHourRange() {
        String expected = "Select c from Crash c where hour(c.crashDateTime) between :startHour and :endHour";
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addStartHour(8)
                .addEndHour(17)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForCrashSeveritiesAndHourRange() {
        String expected = "Select c from Crash c where c.crashSeverity in (:crashSeverityList) and " +
                "hour(c.crashDateTime) between :startHour and :endHour";
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
        String expected = "Select c from Crash c join c.vehicles v where v.vehicleType in (:vehicleTypeList)";
        List<VehicleType> vehicleTypes = getVehicleTypes();
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addQueryable(vehicleTypes)
                .joinVehicles(true)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForCrashesInvolvingPedestrians() {
        String expected = "Select c from Crash c join c.casualties i where i.casualtyClass in (:casualtyClassList)";
        List<CasualtyClass> casualtyClasses = getCasualtyClasses();
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .addQueryable(casualtyClasses)
                .joinCasualties(true)
                .build();
        assertEquals(expected, crashQuery.toString());
    }

    @Test
    public void testHqlQueryGeneratedForCrashesInvolvingFemaleDriversAbove30Yrs() {
        String expected = "Select c from Crash c join c.vehicles v where v.driver.age > :age and v.driver.gender = :gender";
        crashQuery = new CrashQuery.CrashQueryBuilder()
                .joinVehicles(true)
                .addCustomQueryable(CrashQuery.CrashQueryBuilder.CrashJoinType.VEHICLE,
                        "driver.age", Comparison.GT, "age", 30, false)
                .addCustomQueryable(CrashQuery.CrashQueryBuilder.CrashJoinType.VEHICLE,
                        "driver.gender", Comparison.EQ, "gender", "F", false)
                .build();
        assertEquals(expected, crashQuery.toString());
    }
}
