package com.sweroad.webapp.controller;

import com.sweroad.model.*;
import com.sweroad.query.CrashQuery;
import com.sweroad.query.CrashSearch;
import com.sweroad.service.CrashQueryService;
import com.sweroad.service.CrashService;
import com.sweroad.webapp.util.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by Frank on 1/6/16.
 */
@Controller
@RequestMapping("/crashselection*")
public class CrashSelectionController extends BaseFormController {

    @Autowired
    private CrashService crashService;
    @Autowired
    private CrashQueryService crashQueryService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView crashSelection() throws Exception {
        ModelAndView mav = new ModelAndView("selection/crashselection");
        mav.addAllObjects(crashService.getOrderedRefData());
        return mav;
    }

    @RequestMapping(value = "/crashselectioncrashes", method = RequestMethod.GET)
    public void selectCrashes(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam("recent") Boolean recent) throws Exception {
        SessionHelper.persistCrashesInSession(request, crashService.getAvailableCrashes(recent));
        response.sendRedirect(request.getContextPath() + "/analysis");
    }

    @RequestMapping(value = "/crashselectionseverity", method = RequestMethod.GET)
    public void selectCrashesBySeverity(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam("id") Long id) throws Exception {
        if (id == null) {
            response.sendRedirect(request.getContextPath() + "/crashselection");
        } else {
            CrashSearch crashSearch = new CrashSearch();
            crashSearch.getCrashSeverities().add(new CrashSeverity(id));
            getCrashes(request, response, crashSearch);
        }
    }

    @RequestMapping(value = "/crashselectioncollisiontype", method = RequestMethod.GET)
    public void selectCrashesByCollisionType(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam("id") Long id) throws Exception {
        if (id == null) {
            response.sendRedirect(request.getContextPath() + "/crashselection");
        } else {
            CrashSearch crashSearch = new CrashSearch();
            crashSearch.getCollisionTypes().add(new CollisionType(id));
            getCrashes(request, response, crashSearch);
        }
    }

    @RequestMapping(value = "/crashselectioncause", method = RequestMethod.GET)
    public void selectCrashesByCause(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam("id") Long id) throws Exception {
        if (id == null) {
            response.sendRedirect(request.getContextPath() + "/crashselection");
        } else {
            CrashSearch crashSearch = new CrashSearch();
            crashSearch.getCrashCauses().add(new CrashCause(id));
            getCrashes(request, response, crashSearch);
        }
    }

    @RequestMapping(value = "/crashselectiondistrict", method = RequestMethod.GET)
    public void selectCrashesByDistrict(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam("id") Long id) throws Exception {
        if (id == null) {
            response.sendRedirect(request.getContextPath() + "/crashselection");
        } else {
            CrashSearch crashSearch = new CrashSearch();
            crashSearch.getDistricts().add(new District(id));
            getCrashes(request, response, crashSearch);
        }
    }

    @RequestMapping(value = "/crashselectionvehicletype", method = RequestMethod.GET)
    public void selectCrashesByVehicleType(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam("id") Long id) throws Exception {
        if (id == null) {
            response.sendRedirect(request.getContextPath() + "/crashselection");
        } else {
            CrashSearch crashSearch = new CrashSearch();
            crashSearch.getVehicleTypes().add(new VehicleType(id));
            getCrashes(request, response, crashSearch);
        }
    }

    @RequestMapping(value = "/crashselectioncasualtyclass", method = RequestMethod.GET)
    public void selectCrashesByCasualtyClass(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam("id") Long id) throws Exception {
        if (id == null) {
            response.sendRedirect(request.getContextPath() + "/crashselection");
        } else {
            CrashSearch crashSearch = new CrashSearch();
            crashSearch.getCasualtyClasses().add(new CasualtyClass(id));
            getCrashes(request, response, crashSearch);
        }
    }

    private void getCrashes(HttpServletRequest request, HttpServletResponse response, CrashSearch crashSearch) throws IOException {
        CrashQuery crashQuery = crashSearch.toQuery();
        SessionHelper.persistCrashesInSession(request, crashQueryService.getCrashesByQuery(crashQuery), crashQuery);
        response.sendRedirect(request.getContextPath() + "/analysis");
    }
}