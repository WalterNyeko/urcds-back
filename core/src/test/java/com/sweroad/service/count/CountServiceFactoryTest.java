package com.sweroad.service.count;

import com.sweroad.model.AttributeType;
import com.sweroad.service.BaseManagerTestCase;
import com.sweroad.service.count.impl.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

/**
 * Created by Frank on 6/30/16.
 */
public class CountServiceFactoryTest extends BaseManagerTestCase {

    @Autowired
    private CountServiceFactory countServiceFactory;

    @Test
    public void testThatFactoryGetsCountWeatherService() {
        assertTrue(countServiceFactory.getCountService(AttributeType.WEATHER) instanceof CountWeatherServiceImpl);
    }

    @Test
    public void testThatFactoryGetsCountDistrictService() {
        assertTrue(countServiceFactory.getCountService(AttributeType.DISTRICT) instanceof CountDistrictServiceImpl);
    }

    @Test
    public void testThatFactoryGetsCountDriverAgeService() {
        assertTrue(countServiceFactory.getCountService(AttributeType.DRIVER_AGE) instanceof CountDriverAgeServiceImpl);
    }

    @Test
    public void testThatFactoryGetsCountCrashCauseService() {
        assertTrue(countServiceFactory.getCountService(AttributeType.CRASH_CAUSE) instanceof CountCrashCauseServiceImpl);
    }

    @Test
    public void testThatFactoryGetsCountVehicleTypeService() {
        assertTrue(countServiceFactory.getCountService(AttributeType.VEHICLE_TYPE) instanceof CountVehicleTypeServiceImpl);
    }

    @Test
    public void testThatFactoryGetsCountRoadSurfaceService() {
        assertTrue(countServiceFactory.getCountService(AttributeType.ROAD_SURFACE) instanceof CountRoadSurfaceServiceImpl);
    }

    @Test
    public void testThatFactoryGetsCountSurfaceTypeService() {
        assertTrue(countServiceFactory.getCountService(AttributeType.SURFACE_TYPE) instanceof CountSurfaceTypeServiceImpl);
    }

    @Test
    public void testThatFactoryGetsCountDriverGenderervice() {
        assertTrue(countServiceFactory.getCountService(AttributeType.DRIVER_GENDER) instanceof CountDriverGenderServiceImpl);
    }

    @Test
    public void testThatFactoryGetsCountJunctionTypeService() {
        assertTrue(countServiceFactory.getCountService(AttributeType.JUNCTION_TYPE) instanceof CountJunctionTypeServiceImpl);
    }

    @Test
    public void testThatFactoryGetsCountCollisionTypeService() {
        assertTrue(countServiceFactory.getCountService(AttributeType.COLLISION_TYPE) instanceof CountCollisionTypeServiceImpl);
    }

    @Test
    public void testThatFactoryGetsCountCrashSeverityService() {
        assertTrue(countServiceFactory.getCountService(AttributeType.CRASH_SEVERITY) instanceof CountCrashSeverityServiceImpl);
    }

    @Test
    public void testThatFactoryGetsCountPoliceStationService() {
        assertTrue(countServiceFactory.getCountService(AttributeType.POLICE_STATION) instanceof CountPoliceStationServiceImpl);
    }

    @Test
    public void testThatFactoryGetsCountDriverBeltUseService() {
        assertTrue(countServiceFactory.getCountService(AttributeType.DRIVER_BELT_USE) instanceof CountDriverBeltUseServiceImpl);
    }

    @Test
    public void testThatFactoryGetsCountRoadwayCharacterService() {
        assertTrue(countServiceFactory.getCountService(AttributeType.ROADWAY_CHARACTER) instanceof CountRoadwayCharacterServiceImpl);
    }

    @Test
    public void testThatFactoryGetsSurfaceConditionService() {
        assertTrue(countServiceFactory.getCountService(AttributeType.SURFACE_CONDITION) instanceof CountSurfaceConditionServiceImpl);
    }

    @Test
    public void testThatFactoryGetsCountDriverLicenseTypeService() {
        assertTrue(countServiceFactory.getCountService(AttributeType.DRIVER_LICENSE_TYPE) instanceof CountDriverLicenseTypeServiceImpl);
    }

    @Test
    public void testThatFactoryGetsCountVehicleFailureTypeService() {
        assertTrue(countServiceFactory.getCountService(AttributeType.VEHICLE_FAILURE_TYPE) instanceof CountVehicleFailureTypeServiceImpl);
    }

    @Test
    public void testThatFactoryGetsCountDriverCasualtyTypeService() {
        assertTrue(countServiceFactory.getCountService(AttributeType.DRIVER_CASUALTY_TYPE) instanceof CountDriverCasualtyTypeServiceImpl);
    }
}
