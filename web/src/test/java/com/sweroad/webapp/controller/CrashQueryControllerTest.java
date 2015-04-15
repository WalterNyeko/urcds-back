package com.sweroad.webapp.controller;

import org.springframework.mock.web.MockHttpServletRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Frank on 12/19/14.
 */
public class CrashQueryControllerTest extends BaseControllerTestCase {

    @Autowired
    private CrashQueryController controller;
    private MockHttpServletRequest request;

    @Test
    public void testThatCrashSearchFormHasCrashSearchModel() {
        log.debug("testing that crash search form has CrashSearch model...");
        request = newGet("/crashquery");
        ModelAndView mav = controller.showForm(request);
        ModelMap mp = mav.getModelMap();
        assertNotNull(mp.get("crashSearch"));
    }

    @Test
    public void testThatCrashSearchFormHasReferenceData() {
        log.debug("testing that crash search form has reference data...");
        request = newGet("/crashquery");
        ModelAndView mav = controller.showForm(request);
        ModelMap mp = mav.getModelMap();
        assertNotNull(mp.get("crashSeverities"));
    }
}
