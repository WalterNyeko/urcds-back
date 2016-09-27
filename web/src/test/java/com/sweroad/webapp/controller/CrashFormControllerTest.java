package com.sweroad.webapp.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.sweroad.model.Casualty;
import com.sweroad.model.Crash;
import com.sweroad.model.Vehicle;

public class CrashFormControllerTest extends BaseControllerTestCase {

	@Autowired
	private CrashFormController crashForm;
	private MockHttpServletRequest request;

	@Test
	public void testThatCrashFormHasCrashModel() throws Exception {
		log.debug("testing that crash form has crash model...");
		ModelAndView mav = crashForm.showForm(newGet("/crashform"));
		ModelMap mp = mav.getModelMap();
		assertNotNull(mp.get("crash"));
	}

	@Test
	public void testThatCrashFormHasReferenceData() throws Exception {
		log.debug("testing that crash form has reference data...");
		ModelAndView mav = crashForm.showForm(newGet("/crashform"));
		ModelMap mp = mav.getModelMap();
		assertNotNull(mp.get("crashSeverities"));
	}

	@Test
	public void testThatShowCrashFormWithIdPutsCrashInSession()
			throws Exception {
		log.debug("testing that show crash form with id puts crash in session...");
		request = newGet("/crashform");
		Crash crash = new Crash();
		crash.setId(1L);
		request.addParameter("id", crash.getId().toString());
		crashForm.showForm(request);
		Crash sessionCrash = (Crash) request.getSession().getAttribute("crash");
		assertEquals(crash, sessionCrash);
	}

	@Test
	public void testThatPostTo2ndCrashFormPagePutsCrashInSession()
			throws Exception {
		log.debug("testing that second page of crash form puts crash in session...");
		request = newPost("/crashform2");
		Crash crash = new Crash();
		BindingResult errors = new DataBinder(crash).getBindingResult();
		crashForm.showForm2(crash, errors, request,
				new MockHttpServletResponse());
		assertNotNull(request.getSession().getAttribute("crash"));
	}

	@Test
	public void testThatPostTo2ndCrashFormPageInEditModeMergesWithSessionData()
			throws Exception {
		log.debug("testing that post to second page of crash form merges the passed Crash object "
				+ "with the session Crash object");
		request = newPost("/crashform2");
		Crash sessionCrash = new Crash();
		sessionCrash.setId(5L);
		sessionCrash.setTarNo("oldTAR");
		sessionCrash.addCasualty(new Casualty());
		sessionCrash.addVehicle(new Vehicle());
		request.getSession().setAttribute("crash", sessionCrash);

		Crash crashInEdit = new Crash();
		crashInEdit.setId(5L);
		crashInEdit.setTarNo("newTAR");

		BindingResult errors = new DataBinder(crashInEdit).getBindingResult();
		crashForm.showForm2(crashInEdit, errors, request,
				new MockHttpServletResponse());

		Crash newSessionCrash = (Crash) request.getSession().getAttribute(
				"crash");
		boolean crashesMerged = newSessionCrash.getTarNo().equals(
				crashInEdit.getTarNo());
		crashesMerged = crashesMerged
				&& newSessionCrash.getCasualties().size() == 1;
		crashesMerged = crashesMerged
				&& newSessionCrash.getVehicles().size() == 1;
		assertTrue(crashesMerged);
	}

	@Test
	public void testDeleteCasualty() throws Exception {
		log.debug("testing delete casualty...");
		request = newGet("/crashformcasualtydelete");
		request.addParameter("id", "2");
		Crash crash = new Crash();
		crash.addCasualty(new Casualty());
		crash.addCasualty(new Casualty());
		crash.getCasualties().get(0).setId(1L);
		crash.getCasualties().get(1).setId(2L);
		request.getSession().setAttribute("crash", crash);
		crashForm.deleteCasualty(request);
		Crash newSessionCrash = (Crash) request.getSession().getAttribute(
				"crash");
		assertEquals(1, newSessionCrash.getCasualties().size());
	}
	
	@Test
	public void testDeleteVehilce() throws Exception {
		log.debug("testing delete vehicle...");
		request = newGet("/crashformvehicledelete");
		request.addParameter("id", "2");
		Crash crash = new Crash();
		crash.addVehicle(new Vehicle());
		crash.addVehicle(new Vehicle());
		crash.getVehicles().get(0).setId(1L);
		crash.getVehicles().get(1).setId(2L);
		request.getSession().setAttribute("crash", crash);
		crashForm.deleteVehicle(request);
		Crash newSessionCrash = (Crash) request.getSession().getAttribute(
				"crash");
		assertEquals(1, newSessionCrash.getVehicles().size());
	}
}
