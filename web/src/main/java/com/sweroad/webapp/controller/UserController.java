package com.sweroad.webapp.controller;

import com.sweroad.Constants;
import com.sweroad.dao.SearchException;
import com.sweroad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * Simple class to retrieve a list of users from the database.
 * <p/>
 * <p>
 * <a href="UserController.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/admin/users*")
public class UserController {
    private UserService userService = null;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(@RequestParam(required = false, value = "q") String query) throws Exception {
        Model model = new ExtendedModelMap();
        try {
            model.addAttribute(Constants.USER_LIST, userService.search(query));
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(userService.getUsers());
        }
        return new ModelAndView("admin/userList", model.asMap());
    }
}
