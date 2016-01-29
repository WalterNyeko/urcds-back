package com.sweroad.webapp.controller;

import com.sweroad.model.SystemParameter;
import com.sweroad.service.GenericManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * Created by Frank on 1/27/16.
 */
@Controller
@RequestMapping("/admin/params*")
public class SystemParameterController extends BaseFormController {

    @Autowired
    private GenericManager<SystemParameter, Long> systemParameterManager;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showParams(HttpServletRequest request) throws Exception {
        try {
            List<SystemParameter> systemParameters = systemParameterManager.getAllDistinct();
            Collections.sort(systemParameters);
            return new ModelAndView("admin/parameters").addObject("parameters", systemParameters);
        } catch (Exception e) {
            logException(request, e, "Loading system parameters encountered a problem");
            return new ModelAndView("admin/parameters");
        }
    }
}
