package com.sweroad.webapp.controller;

import com.sweroad.model.Crash;
import com.sweroad.model.CrashSeverity;
import com.sweroad.service.CrashService;
import com.sweroad.service.GenericService;
import com.sweroad.webapp.util.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/mapping*")
public class CrashMapController extends BaseFormController {

    @Autowired
    private GenericService<CrashSeverity, Long> crashSeverityService;
    @Autowired
    private CrashService crashService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showMap(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("mapping/crashmap");
        mav.addObject("crashSeverities", crashSeverityService.getAll());
        List<Crash> crashes = (List<Crash>) request.getSession().getAttribute("crashes");
        if (crashes == null) {
            crashes = crashService.getAvailableCrashes(true);
            SessionHelper.persistCrashesInSession(request, crashes);
            SessionHelper.persistCrashAttributesInSession(request, crashService.getOrderedRefData());
        }
        return mav;
    }
}