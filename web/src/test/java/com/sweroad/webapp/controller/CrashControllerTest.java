package com.sweroad.webapp.controller;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

public class CrashControllerTest extends BaseControllerTestCase {

	@Autowired
	private CrashController controller;
	
	@Test
	public void testThatHandleRequestWorksFine() throws Exception {
		ModelAndView mav = controller.handleRequest();
		ModelMap mp = mav.getModelMap();
		assertNotNull(mp.get("crashList"));
	}
}