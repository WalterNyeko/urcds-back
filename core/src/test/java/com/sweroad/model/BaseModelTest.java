package com.sweroad.model;

import com.sweroad.service.BaseManagerTestCase;
import com.sweroad.service.CrashService;
import com.sweroad.service.GenericService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * Created by Frank on 8/31/15.
 */
public class BaseModelTest extends BaseManagerTestCase {

    @Autowired
    private CrashService crashService;
    @Autowired
    private GenericService<Driver, Long> driverService;
    @Autowired
    private GenericService<Vehicle, Long> vehicleManager;
    @Autowired
    private GenericService<Casualty, Long> casualtyManager;
    @Autowired
    private GenericService<CasualtyClass, Long> casualtyClassService;
    @Autowired
    private GenericService<PoliceStation, Long> policeStationService;

    @Test
    public void testNameIdModelJSON() {
        CasualtyClass casualtyClass = casualtyClassService.get(1L);
        String expected = "{\"id\":1,\"name\":\"Pedestrian\"}";
        assertEquals(expected, casualtyClass.toJSON());
    }

    @Test
    public void testPoliceStationJSON() {
        PoliceStation policeStation = policeStationService.get(1L);
        String expected = "{\"id\":1,\"name\":\"Lugazi Police Station\",\"district\":{\"id\":1,\"name\":\"Lugazi\"}}";
        assertEquals(expected, policeStation.toJSON());
    }

    @Test
    public void testDriverJSON() {
        Driver driver = driverService.get(1L);
        String expected = "{\"id\":1,\"age\":27,\"gender\":\"M\",\"licenseValid\":1,\"beltOrHelmetUsed\":0," +
                "\"licenseNumber\":\"123456\",\"casualtyType\":{\"id\":4,\"name\":\"Not injured\"}}";
        assertEquals(expected, driver.toJSON());
    }

    @Test
    public void testVehicleJSON() {
        Vehicle vehicle = vehicleManager.get(1L);
        String expected = "{\"id\":1,\"number\":1,\"driver\":{\"id\":1,\"age\":27,\"gender\":\"M\",\"licenseValid\":1," +
                "\"beltOrHelmetUsed\":0,\"licenseNumber\":\"123456\",\"casualtyType\":{\"id\":4,\"name\":\"Not injured\"}}," +
                "\"vehicleType\":null,\"companyName\":\"\"}";
        assertEquals(expected, vehicle.toJSON());
    }

    @Test
    public void testCasualtyJSON() {
        Casualty casualty = casualtyManager.get(1L);
        String expected = "{\"id\":1,\"age\":25,\"gender\":\"F\",\"beltOrHelmetUsed\":0,\"casualtyType\":{\"id\":5,\"name\":\"Unknown\"}," +
                "\"casualtyClass\":{\"id\":4,\"name\":\"Car passenger\"},\"vehicle\":{\"id\":1,\"number\":1," +
                "\"driver\":{\"id\":1,\"age\":27,\"gender\":\"M\",\"licenseValid\":1,\"beltOrHelmetUsed\":0,\"licenseNumber\":\"123456\"," +
                "\"casualtyType\":{\"id\":4,\"name\":\"Not injured\"}},\"vehicleType\":null,\"companyName\":\"\"}}";
        assertEquals(expected, casualty.toJSON());
    }

    @Test
    public void testAgeRangeJSON() {
        AgeRange ageRange = new AgeRange(1L, 19, 25);
        String expected = "{\"id\":1,\"minAge\":19,\"maxAge\":25,\"label\":\"19 - 25\"}";
        assertEquals(expected, ageRange.toJSON());
    }

    @Test
    public void testCrashJSON() {
        Crash crash = crashService.get(1L);
        String expected = "{\"id\":1,\"road\":\"Kampala-Jinja\",\"tarNo\":\"A1509/LGZ\",\"weight\":null,\"weather\":{\"id\":1,\"name\":\"Clear\"}," +
                "\"vehicles\":[{\"id\":1,\"number\":1,\"driver\":{\"id\":1,\"age\":27,\"gender\":\"M\",\"licenseValid\":1,\"beltOrHelmetUsed\":0," +
                "\"licenseNumber\":\"123456\",\"casualtyType\":{\"id\":4,\"name\":\"Not injured\"}},\"vehicleType\":null," +
                "\"companyName\":\"\"},{\"id\":2,\"number\":2,\"driver\":{\"id\":2,\"age\":53,\"gender\":\"M\"," +
                "\"licenseValid\":0,\"beltOrHelmetUsed\":1,\"licenseNumber\":\"123789\",\"casualtyType\":{\"id\":3,\"name\":\"Slight\"}}," +
                "\"vehicleType\":{\"id\":7,\"name\":\"Medium Omnibus\"},\"companyName\":\"Total Uganda\"}],\"latitude\":\"N00 23.000\"," +
                "\"longitude\":\"E032 55.270\",\"casualties\":[{\"id\":1,\"age\":25,\"gender\":\"F\",\"beltOrHelmetUsed\":0," +
                "\"casualtyType\":{\"id\":5,\"name\":\"Unknown\"},\"casualtyClass\":{\"id\":4,\"name\":\"Car passenger\"}," +
                "\"vehicle\":{\"id\":1,\"number\":1,\"driver\":{\"id\":1,\"age\":27,\"gender\":\"M\",\"licenseValid\":1,\"beltOrHelmetUsed\":0," +
                "\"licenseNumber\":\"123456\",\"casualtyType\":{\"id\":4,\"name\":\"Not injured\"}},\"vehicleType\":null," +
                "\"companyName\":\"\"}},{\"id\":2,\"age\":30,\"gender\":\"M\",\"beltOrHelmetUsed\":3," +
                "\"casualtyType\":{\"id\":2,\"name\":\"Serious\"},\"casualtyClass\":{\"id\":1,\"name\":\"Pedestrian\"},\"vehicle\":null}]," +
                "\"crashCause\":{\"id\":1,\"name\":\"Careless overtaking\"},\"roadNumber\":\"15\",\"crashPlace\":\"Kayanja\"," +
                "\"roadSurface\":{\"id\":2,\"name\":\"Dry\"},\"surfaceType\":{\"id\":1,\"name\":\"Tar\"},\"junctionType\":{\"id\":1," +
                "\"name\":\"Not in junction\"},\"policeStation\":{\"id\":1,\"name\":\"Lugazi Police Station\",\"district\":{\"id\":1," +
                "\"name\":\"Lugazi\"}},\"townOrVillage\":\"Kayanja\",\"crashSeverity\":{\"id\":2,\"name\":\"Serious\"}," +
                "\"collisionType\":{\"id\":6,\"name\":\"Angle\"},\"latitudeNumeric\":0.383333,\"longitudeNumeric\":32.921167," +
                "\"surfaceCondition\":{\"id\":1,\"name\":\"Good\"},\"roadwayCharacter\":{\"id\":2,\"name\":\"Blind bend\"}," +
                "\"crashDateTime\":\"2014-06-03\",\"vehicleFailureType\":{\"id\":1,\"name\":\"No mechanical defects\"}," +
                "\"crashDateTimeString\":\"2014-06-03 16:21\"}";
        assertEquals(expected, crash.toJSON());
    }
}
