package com.sweroad.query;

import com.sweroad.model.CollisionType;
import com.sweroad.model.CrashSeverity;
import com.sweroad.model.Weather;
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
public class QueryCrashTest extends BaseManagerTestCase {

    private QueryCrash queryCrash;

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

    @Test
    public void testHqlQueryGeneratedForCrashSeverities() {
        String expected = "from Crash c where c.crashSeverity in (:crashSeverityList)";
        List<CrashSeverity> severities = getCrashSeverities();
        queryCrash = new QueryCrash.QueryCrashBuilder().addQueryable(severities).build();
        assertEquals(expected, queryCrash.generateHQL());
    }

    @Test
    public void testHqlQueryGeneratedForCrashSeveritiesAndCollisionTypes() {
        String expected = "from Crash c where c.collisionType in (:collisionTypeList) and " +
                "c.crashSeverity in (:crashSeverityList)";
        List<CrashSeverity> severities = getCrashSeverities();
        List<CollisionType> collisionTypes = getCollisionTypes();
        queryCrash = new QueryCrash.QueryCrashBuilder()
                .addQueryable(severities)
                .addQueryable(collisionTypes)
                .build();
        assertEquals(expected, queryCrash.generateHQL());
    }

    @Test
    public void testHqlQueryGeneratedForCrashSeveritiesCollisionTypesAndWeather() {
        String expected = "from Crash c where c.collisionType in (:collisionTypeList) and " +
                "c.crashSeverity in (:crashSeverityList) and " +
                "c.weather in (:weatherList)";
        List<CrashSeverity> severities = getCrashSeverities();
        List<CollisionType> collisionTypes = getCollisionTypes();
        List<Weather> weatherList = getWeatherList();
        queryCrash = new QueryCrash.QueryCrashBuilder()
                .addQueryable(severities)
                .addQueryable(collisionTypes)
                .addQueryable(weatherList)
                .build();
        assertEquals(expected, queryCrash.generateHQL());
    }

    @Test
    public void testHqlQueryGeneratedForStartDate() throws Exception {
        String expected = "from Crash c where c.crashDateTime >= :startDate";
        Date startDate = DateUtil.convertStringToDate("01/06/2014");
        queryCrash = new QueryCrash.QueryCrashBuilder()
                .addStartDate(startDate)
                .build();
        assertEquals(expected, queryCrash.generateHQL());
    }

//    @Test
//    public void testHqlQueryGeneratedForEndDate() throws Exception {
//        String expected = "from Crash c where c.crashDateTime <= :endDate";
//        Date endDate = DateUtil.convertStringToDate("31/12/2014");
//        queryCrash = new QueryCrash.QueryCrashBuilder()
//                .addEndDate(endDate)
//                .build();
//        assertEquals(expected, queryCrash.generateHQL());
//    }

//    @Test
//    public void testHqlQueryGeneratedForDateRange() throws Exception {
//        String expected = "from Crash c where c.crashDateTime between :startDate and :endDate";
//        Date startDate = DateUtil.convertStringToDate("01/06/2014");
//        Date endDate = DateUtil.convertStringToDate("31/12/2014");
//        queryCrash = new QueryCrash.QueryCrashBuilder()
//                .addStartDate(startDate)
//                .addEndDate(endDate)
//                .build();
//        assertEquals(expected, queryCrash.generateHQL());
//    }

    @Test
    public void testHqlQueryGeneratedForStartMonth() throws Exception {
        String expected = "from Crash c where ((month(c.crashDateTime) >= month(:startDate) and " +
                "year(c.crashDateTime) = year(:startDate)) or year(c.crashDateTime) > year(:startDate)";
        Date startDate = DateUtil.convertStringToDate("01/06/2014");
        queryCrash = new QueryCrash.QueryCrashBuilder()
                .addStartDate(startDate)
                .setUseMonth(true)
                .build();
        assertEquals(expected, queryCrash.generateHQL());
    }

    @Test
    public void testHqlQueryGeneratedForEndMonth() throws Exception {
        String expected = "from Crash c where ((month(c.crashDateTime) <= month(:endDate) and " +
                "year(c.crashDateTime) = year(:endDate)) or year(c.crashDateTime) < year(:endDate)";
        Date endDate = DateUtil.convertStringToDate("31/12/2014");
        queryCrash = new QueryCrash.QueryCrashBuilder()
                .addEndDate(endDate)
                .setUseMonth(true)
                .build();
        assertEquals(expected, queryCrash.generateHQL());
    }

//    @Test
//    public void testHqlQueryGeneratedForMonthRange() throws Exception {
//        String expected = "from Crash c where (((month(c.crashDateTime) >= month(:startDate) and " +
//                "year(c.crashDateTime) = year(:startDate)) or year(c.crashDateTime) > year(:startDate)) " +
//                "and (((month(c.crashDateTime) <= month(:endDate) and " +
//                "year(c.crashDateTime) = year(:endDate)) or year(c.crashDateTime) < year(:endDate))";
//        Date startDate = DateUtil.convertStringToDate("01/06/2014");
//        Date endDate = DateUtil.convertStringToDate("31/12/2014");
//        queryCrash = new QueryCrash.QueryCrashBuilder()
//                .addStartDate(startDate)
//                .addEndDate(endDate)
//                .setUseMonth(true)
//                .build();
//        assertEquals(expected, queryCrash.generateHQL());
//    }

    @Test
    public void testHqlQueryGeneratedForStartYear() throws Exception {
        String expected = "from Crash c where year(c.crashDateTime) >= year(:startDate)";
        Date startDate = DateUtil.convertStringToDate("01/06/2014");
        queryCrash = new QueryCrash.QueryCrashBuilder()
                .addStartDate(startDate)
                .setUseYear(true)
                .build();
        assertEquals(expected, queryCrash.generateHQL());
    }

//    @Test
//    public void testHqlQueryGeneratedForEndYear() throws Exception {
//        String expected = "from Crash c where year(c.crashDateTime) <= year(:endDate)";
//        Date endDate = DateUtil.convertStringToDate("31/12/2014");
//        queryCrash = new QueryCrash.QueryCrashBuilder()
//                .addEndDate(endDate)
//                .setUseYear(true)
//                .build();
//        assertEquals(expected, queryCrash.generateHQL());
//    }

//    @Test
//    public void testHqlQueryGeneratedForYearRange() throws Exception {
//        String expected = "from Crash c where year(c.crashDateTime) between year(:startDate) and year(:endDate)";
//        Date startDate = DateUtil.convertStringToDate("01/06/2014");
//        Date endDate = DateUtil.convertStringToDate("31/12/2014");
//        queryCrash = new QueryCrash.QueryCrashBuilder()
//                .addStartDate(startDate)
//                .addEndDate(endDate)
//                .setUseYear(true)
//                .build();
//        assertEquals(expected, queryCrash.generateHQL());
//    }
}
