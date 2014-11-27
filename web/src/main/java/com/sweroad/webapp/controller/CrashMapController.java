package com.sweroad.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mapping*")
public class CrashMapController extends BaseFormController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showMap(HttpServletRequest request) throws Exception {
        return new ModelAndView("mapping/crashmap");
    }
}
