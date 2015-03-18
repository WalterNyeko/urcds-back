package com.sweroad.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Frank on 3/18/15.
 */
@Controller
@RequestMapping("/injuries*")
public class InjuryController extends BaseFormController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showInjuries(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("injury/injuries");
        return mav;
    }
}
