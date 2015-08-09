package com.sweroad.service;

import static org.junit.Assert.assertEquals;

import com.sweroad.Constants;
import com.sweroad.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

public class CrashManagerTest extends BaseManagerTestCase {

	private Log log = LogFactory.getLog(CrashManagerTest.class);
	@Autowired
	private CrashManager crashManager;
    private SecurityContext initialSecurityContext = null;

    @Before
    public void setUp() {
        initialSecurityContext = SecurityContextHolder.getContext();

        SecurityContext context = new SecurityContextImpl();
        User user = new User("user");
        user.setId(1L);
        user.setPassword("password");
        user.addRole(new Role(Constants.USER_ROLE));

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        token.setDetails(user);
        context.setAuthentication(token);
        SecurityContextHolder.setContext(context);
    }

    @After
    public void tearDown() {
        SecurityContextHolder.setContext(initialSecurityContext);
    }

	@Test
	public void testRemoveCasualtyFromCrash() {
		log.debug("testing remove casualty from crash...");
		Crash crash = new Crash();
		crash.addCasualty(new Casualty());
		crash.addCasualty(new Casualty());
		crash.getCasualties().get(0).setId(1L);
		crash.getCasualties().get(1).setId(2L);
		crashManager.removeCasualtyFromCrash(crash, 2L);
		assertEquals(1, crash.getCasualties().size());
	}

	@Test
	public void testRemoveVehicleFromCrash() {
		log.debug("testing remove vehicle from crash...");
		Crash crash = new Crash();
		crash.addVehicle(new Vehicle());
		crash.addVehicle(new Vehicle());
		crash.getVehicles().get(0).setId(1L);
		crash.getVehicles().get(1).setId(2L);
		crashManager.removeVehicleFromCrash(crash, 2L);
		assertEquals(1, crash.getVehicles().size());
	}

	@Test
	public void testThatRemoveVehicleFromCrashRemovesReferencedCasualties() {
		log.debug("testing that remove vehicle from crash removes vehicle referenced casualties...");
		Crash crash = new Crash();
		crash.addVehicle(new Vehicle());
		crash.addVehicle(new Vehicle());
		crash.getVehicles().get(0).setId(1L);
		crash.getVehicles().get(1).setId(2L);

		crash.addCasualty(new Casualty());
		crash.addCasualty(new Casualty());
		crash.getCasualties().get(0).setId(1L);
		crash.getCasualties().get(1).setId(2L);
		crash.getCasualties().get(0).setVehicle(crash.getVehicles().get(0));
		crash.getCasualties().get(1).setVehicle(crash.getVehicles().get(1));

		crashManager.removeVehicleFromCrash(crash, 2L);
		assertEquals(1, crash.getCasualties().size());
	}
	
	@Test
	public void testThatRemoveCrashSetsCrashToRemoved() {
		log.debug("testing that remove crash sets crash status to removed...");
		Crash crash = crashManager.get(1L);
		crash.setRemoved(false);
		crashManager.save(crash);
		crashManager.removeCrashById(1L);
		crash  = crashManager.get(1L);
		assertEquals(true, crash.isRemoved());
	}
	
	@Test
	public void testThatRestoreCrashSetsCrashIsRemovedToFalse() {
		log.debug("testing that restore crash sets crash isRemoved to false...");
		Crash crash = crashManager.get(1L);
		crash.setRemoved(true);
		crashManager.save(crash);
		crashManager.restoreCrashById(1L);
		crash  = crashManager.get(1L);
		assertEquals(false, crash.isRemoved());
	}

    @Test
    public void testSaveCrash() {
        log.debug("testing save crash...");
        Crash crash = buildCrashObject();
        crash = crashManager.saveCrash(crash);
        assertEquals(11L, crash.getId().longValue());
    }

    private Crash buildCrashObject() {
        Crash crash = new Crash();
        crash.setId(0L);
        crash.setTarNo("A001/Test1");
        crash.setPoliceStation(new PoliceStation());
        crash.setLatitude("Latitude");
        crash.setLongitude("Longitude");
        crash.getPoliceStation().setId(1L);
        for (int i = 1; i <= 10; i++) {
            Vehicle vehicle = buildVehicleObject(i, i);
            crash.addVehicle(vehicle);
            crash.addCasualty(buildCasualtyObject(vehicle));
        }
        return crash;
    }

    private Vehicle buildVehicleObject(long vehicleAndDriverId, int vehicleNumber) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleAndDriverId);
        vehicle.setVehicleType(new VehicleType());
        vehicle.getVehicleType().setId(1L);
        vehicle.setNumber(vehicleNumber);
        vehicle.setDriver(new Driver());
        vehicle.getDriver().setId(vehicleAndDriverId);
        return vehicle;
    }

    private Casualty buildCasualtyObject(Vehicle vehicle) {
        Casualty casualty = new Casualty();
        casualty.setId(vehicle.getId());
        casualty.setVehicle(vehicle);
        casualty.setCasualtyType(new CasualtyType());
        casualty.getCasualtyType().setId(3L);
        casualty.setCasualtyClass(new CasualtyClass());
        casualty.getCasualtyClass().setId(4L);
        return casualty;
    }
}
