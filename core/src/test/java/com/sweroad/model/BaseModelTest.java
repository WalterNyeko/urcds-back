package com.sweroad.model;

import com.sweroad.service.BaseManagerTestCase;
import com.sweroad.service.GenericManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * Created by Frank on 8/31/15.
 */
public class BaseModelTest extends BaseManagerTestCase {

    @Autowired
    private GenericManager<Driver, Long> driverManager;
    @Autowired
    private GenericManager<Vehicle, Long> vehicleManager;
    @Autowired
    private GenericManager<Casualty, Long> casualtyManager;
    @Autowired
    private GenericManager<CasualtyClass, Long> casualtyClassManager;
    @Autowired
    private GenericManager<PoliceStation, Long> policeStationManager;

    @Test
    public void testNameIdModelJSON() {
        CasualtyClass casualtyClass = casualtyClassManager.get(1L);
        String expected = "{\"id\":1,\"name\":\"Pedestrian\"}";
        assertEquals(expected, casualtyClass.toJSON());
    }

    @Test
    public void testPoliceStationJSON() {
        PoliceStation policeStation = policeStationManager.get(1L);
        String expected = "{\"id\":1,\"name\":\"Lugazi Police Station\",\"district\":{\"id\":1,\"name\":\"Lugazi\"}}";
        assertEquals(expected, policeStation.toJSON());
    }

    @Test
    public void testDriverJSON() {
        Driver driver = driverManager.get(1L);
        String expected = "{\"id\":1,\"age\":27,\"gender\":\"M\",\"licenseValid\":true," +
                "\"licenseNumber\":\"123456\",\"casualtyType\":{\"id\":4,\"name\":\"Not injured\"}}";
        assertEquals(expected, driver.toJSON());
    }

    @Test
    public void testVehicleJSON() {
        Vehicle vehicle = vehicleManager.get(1L);
        String expected = "{\"id\":1,\"number\":1,\"driver\":{\"id\":1,\"age\":27,\"gender\":\"M\",\"licenseValid\":true," +
                "\"licenseNumber\":\"123456\",\"casualtyType\":{\"id\":4,\"name\":\"Not injured\"}}," +
                "\"vehicleType\":{\"id\":1,\"name\":\"Motor car\"},\"companyName\":\"\"}";
        assertEquals(expected, vehicle.toJSON());
    }

    @Test
    public void testCasualtyJSON() {
        Casualty casualty = casualtyManager.get(1L);
        String expected = "{\"id\":1,\"age\":25,\"gender\":\"F\",\"beltOrHelmetUsed\":true,\"casualtyType\":{\"id\":3,\"name\":\"Slight\"}," +
                "\"casualtyClass\":{\"id\":4,\"name\":\"Car passenger\"},\"vehicle\":{\"id\":1,\"number\":1," +
                "\"driver\":{\"id\":1,\"age\":27,\"gender\":\"M\",\"licenseValid\":true,\"licenseNumber\":\"123456\"," +
                "\"casualtyType\":{\"id\":4,\"name\":\"Not injured\"}},\"vehicleType\":{\"id\":1,\"name\":\"Motor car\"}," +
                "\"companyName\":\"\"}}";
        assertEquals(expected, casualty.toJSON());
    }
}
