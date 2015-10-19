package com.sweroad.webapp.controller;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

public class CrashControllerTest extends BaseControllerTestCase {

	@Autowired
	private CrashController controller;
    private MockHttpServletRequest request;
	
	@Test
	public void testThatHandleRequestWorksFine() throws Exception {
		ModelAndView mav = controller.showCrashes(request);
		ModelMap mp = mav.getModelMap();
		assertNotNull(mp.get("crashes"));
	}
}