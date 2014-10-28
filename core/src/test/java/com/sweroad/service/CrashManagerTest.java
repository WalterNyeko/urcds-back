package com.sweroad.service;

import static org.junit.Assert.assertEquals;

import com.sweroad.Constants;
import com.sweroad.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

public class CrashManagerTest extends BaseManagerTestCase {

	private Log log = LogFactory.getLog(CrashManagerTest.class);
	@Autowired
	private CrashManager crashManager;

    private ApplicationContext ctx = null;
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
}
