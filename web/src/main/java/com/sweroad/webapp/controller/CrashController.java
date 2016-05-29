package com.sweroad.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sweroad.service.CrashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sweroad.model.Crash;

@Controller
@RequestMapping("/crash*")
public class CrashController extends BaseFormController {

    @Autowired
    private CrashService crashService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showCrashes(HttpServletRequest request) throws Exception {
        try {
            boolean latestOnly = request.getParameter("all") == null;
            boolean removedOnly = request.getParameter("removed") != null;
            if (removedOnly) {
                return new ModelAndView("crashes").addObject("crashes", crashService.getRemovedCrashes());
            } else {
                return new ModelAndView("crashes").addObject("crashes", crashService.getCrashes(latestOnly));
            }
        } catch (Exception e) {
            logException(request, e, "Load crashes encountered a problem");
            return new ModelAndView("crashes").addObject("crashes", crashService.getCrashes(true));
        }
    }

    @RequestMapping(value = "/crashview", method = RequestMethod.GET)
    public ModelAndView viewCrash(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("crashview");
        try {
            Crash crash;
            String id = request.getParameter("id");
            crash = crashService.getCrashForView(new Long(id));
            mav.addObject("crash", crash);
            mav.addAllObjects(crashService.getReferenceData());
        } catch (Exception e) {
            log.error("View crash failed: " + e.getLocalizedMessage());
            response.sendRedirect(request.getContextPath() + "/crashes");
        }
        return mav;
    }

    @RequestMapping(value = "/crashremove", method = RequestMethod.GET)
    public void removeCrash(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String id = request.getParameter("id");
            crashService.removeCrashById(new Long(id));
            saveMessage(request, "Crash was removed successfully");
        } catch (Exception e) {
            logException(request, e, "Remove crash failed");
        }
        response.sendRedirect(request.getContextPath() + "/crashes");
    }

    @RequestMapping(value = "/crashrestore", method = RequestMethod.GET)
    public void restoreCrash(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String id = request.getParameter("id");
            crashService.restoreCrashById(new Long(id));
            saveMessage(request, "Crash was restored successfully");
        } catch (Exception e) {
            logException(request, e, "Restore crash failed");
        }
        response.sendRedirect(request.getContextPath() + "/crashes");
    }
}
