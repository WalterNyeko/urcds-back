package com.sweroad.webapp.controller;

import com.sweroad.model.CrashSeverity;
import com.sweroad.service.GenericManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mapping*")
public class CrashMapController extends BaseFormController {

    @Autowired
    private GenericManager<CrashSeverity, Long> crashSeverityManager;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showMap(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("mapping/crashmap");
        mav.addObject("crashSeverities", crashSeverityManager.getAll());
        return mav;
    }
}
