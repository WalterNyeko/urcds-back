package com.sweroad.webapp.controller;

import com.sweroad.Constants;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import com.sweroad.service.UserService;

import static org.junit.Assert.*;

public class UserControllerTest extends BaseControllerTestCase {
    @Autowired
    private UserController c;

    @SuppressWarnings("rawtypes")
	@Test
    public void testHandleRequest() throws Exception {
        ModelAndView mav = c.handleRequest(null);
        Map m = mav.getModel();
        assertNotNull(m.get(Constants.USER_LIST));
        assertEquals("admin/userList", mav.getViewName());
    }

    @SuppressWarnings("rawtypes")
	@Test
    public void testSearch() throws Exception {
        // reindex before searching
        UserService userService = (UserService) applicationContext.getBean("userManager");
        userService.reindex();

        ModelAndView mav = c.handleRequest("admin");
        Map m = mav.getModel();
        List results = (List) m.get(Constants.USER_LIST);
        assertNotNull(results);
        assertTrue(results.size() >= 1);
        assertEquals("admin/userList", mav.getViewName());
    }
}
