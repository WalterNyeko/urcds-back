package com.sweroad.webapp.controller;

import com.sweroad.model.Crash;
import com.sweroad.model.PoliceStation;
import com.sweroad.model.Query;
import com.sweroad.query.CrashQuery;
import com.sweroad.query.CrashSearch;
import com.sweroad.service.CrashQueryManager;
import com.sweroad.webapp.util.CrashAnalysisHelper;
import com.sweroad.webapp.util.SessionHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Frank on 12/19/14.
 */
@Controller
@RequestMapping("/crashquery*")
public class CrashQueryController extends BaseFormController {

    @Autowired
    private CrashQueryManager crashQueryManager;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showQueries() {
        ModelAndView mav = new ModelAndView("analysis/queries");
        mav.addObject("queries", crashQueryManager.getCurrentUserQueries());
        return mav;
    }

    @RequestMapping(value = "/crashqueryform", method = RequestMethod.GET)
    public ModelAndView showForm(HttpServletRequest request) {
        clearSession(request);
        ModelAndView mav = new ModelAndView("analysis/crashquery");
        mav.addObject(new CrashSearch());
        String id = request.getParameter("id");
        if (!StringUtils.isBlank(id)) {
            Long queryId = new Long(id);
            Query query = crashQueryManager.getQueryById(queryId);
            request.getSession().setAttribute("query", query);
            mav.addObject("query", query);
        }
        mav.addAllObjects(crashQueryManager.getCrashQueryReferenceData());
        mav.addObject("years", CrashAnalysisHelper.getYearsForSearch());
        mav.addObject("months", CrashAnalysisHelper.getMonthsForSearch(request));
        SessionHelper.persistPoliceStationsInSession(request, (List<PoliceStation>) mav.getModelMap().get("policeStations"));
        return mav;
    }

    @RequestMapping(value = "/crashqueryrun", method = RequestMethod.POST)
    public void runQuery(CrashSearch crashSearch, BindingResult errors, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        try {
            crashQueryManager.processCrashSearch(crashSearch);
            CrashQuery crashQuery = crashSearch.toQuery();
            List<Crash> crashes = crashQueryManager.getCrashesByQuery(crashQuery);
            SessionHelper.persistCrashesInSession(request, crashes, crashQuery);
        } catch (ParseException e) {
            logException(request, e, "Date provided was in wrong format.");
        } catch (Exception e) {
            logException(request, e, "Your query did not run successfully. Please contact your System Administrator.");
        }
        response.sendRedirect(request.getContextPath() + "/analysis");
    }

    @RequestMapping(value="/crashquerysave", method = RequestMethod.POST)
    public ModelAndView saveQuery(HttpServletRequest request, Query query) throws Exception {
        if (request.getSession().getAttribute("query") != null) {
            Query sessionQuery = (Query) request.getSession().getAttribute("query");
            query.setId(sessionQuery.getId());
            query.setDateCreated(sessionQuery.getDateCreated());
        }
        crashQueryManager.saveQuery(query);
        clearSession(request);
        return showQueries();
    }

    @RequestMapping(value = "/crashquerydelete", method = RequestMethod.GET)
    public ModelAndView deleteQuery(HttpServletRequest request) throws Exception {
        try {
            String id = request.getParameter("id");
            crashQueryManager.removeQueryById(new Long(id));
            saveMessage(request, "Query was deleted successfully");
        } catch (Exception e) {
            logException(request, e, "Delete query failed");
        }
        return showQueries();
    }

    private void clearSession(HttpServletRequest request) {
        if (request.getSession().getAttribute("query") != null) {
            request.getSession().removeAttribute("query");
        }
    }
}