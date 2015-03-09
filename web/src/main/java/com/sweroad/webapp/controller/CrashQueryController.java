package com.sweroad.webapp.controller;

import com.sweroad.model.Crash;
import com.sweroad.model.CrashSeverity;
import com.sweroad.query.CrashSearch;
import com.sweroad.service.CrashManager;
import com.sweroad.service.CrashQueryManager;
import com.sweroad.service.LookupManager;
import com.sweroad.webapp.util.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Frank on 12/19/14.
 */
@Controller
@RequestMapping("/crashquery*")
public class CrashQueryController extends  BaseFormController {

    @Autowired
    private CrashQueryManager crashQueryManager;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showForm() {
        ModelAndView mav = new ModelAndView("analysis/crashquery");
        CrashSearch crashSearch = new CrashSearch();
        crashSearch.getCrashSeverities().add(new CrashSeverity());
        crashSearch.getCrashSeverities().get(0).setId(2L);
        mav.addObject(crashSearch);
        mav.addAllObjects(crashQueryManager.getCrashQueryReferenceData());
        return mav;
    }

    @RequestMapping(value = "/crashqueryrun", method = RequestMethod.POST)
    public ModelAndView runQuery(CrashSearch crashSearch, BindingResult errors, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        crashQueryManager.processCrashSearch(crashSearch);
        List<Crash> crashes = crashQueryManager.getCrashesByQuery(crashSearch.toQuery());
        JsonHelper.crashesToJsonAndSetInSession(request, crashes);
        return new CrashAnalysisController().showCrashes(request);
    }
}