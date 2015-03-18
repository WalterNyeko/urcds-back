package com.sweroad.webapp.controller;

import com.sweroad.model.Crash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Frank on 3/18/15.
 */
@Controller
@RequestMapping("/crashstats*")
public class CrashStatisticsController extends BaseFormController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showStatistics(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("analysis/crashstatistics");
        return mav;
    }
}
