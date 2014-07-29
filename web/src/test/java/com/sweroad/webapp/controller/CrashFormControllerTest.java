package com.sweroad.webapp.controller;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.sweroad.model.Crash;

public class CrashFormControllerTest extends BaseControllerTestCase {

	@Autowired
	private CrashFormController crashForm;
	private Crash crash;
	private MockHttpServletRequest request;
	
	@Test
	public void testEdit() throws Exception {
		log.debug("testing edit...");
		request = newGet("/crashform");
		request.addParameter("id", "1");
		ModelAndView mav = crashForm.showForm(request);
		ModelMap mp = mav.getModelMap();
		assertNotNull(mp.get("crash"));
	}
	
	@Test
	public void testSave() throws Exception {
		log.debug("testing save...");
		request = newGet("/crashform");
		ModelAndView mav = crashForm.showForm(request);
		ModelMap mp = mav.getModelMap();
		assertNotNull(mp.get("crash"));
		
//		request = newPost("/crashform");
//		crash = crashForm.showForm(request);
//		//update fields
//		crash.setTarNo("TAR1527/LGZ");
//		crash.setCrashDateTime(new Date());
//		BindingResult errors = new DataBinder(crash).getBindingResult();
//		crashForm.onSubmit(crash, errors, request, new MockHttpServletResponse());
//		assertFalse(errors.hasErrors());
		//assertNotNull(request.getSession().getAttribute("successMessages"));
	}
	
	@Test
	public void testRemove() throws Exception {
		request = newPost("/crashform");
		request.addParameter("delete", "");
		crash = new Crash();
		crash.setId(5L);
		BindingResult errors = new DataBinder("crash").getBindingResult();
		crashForm.onSubmit(crash, errors, request, new MockHttpServletResponse());
		
		//assertNotNull(request.getSession().getAttribute("successMessages"));
	}
}
