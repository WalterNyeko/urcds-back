package com.sweroad.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sweroad.model.Crash;
import com.sweroad.service.CrashManager;

@Controller
@RequestMapping("/crash*")
public class CrashController extends BaseFormController {

    @Autowired
    private CrashManager crashManager;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showCrashes(HttpServletRequest request) throws Exception {
        try {
            boolean latestOnly = request.getParameter("all") == null;
            return new ModelAndView("crashes").addObject("crashes", crashManager.getCrashes(latestOnly));
        } catch (Exception e) {
            log.error("Load crashes encountered a problem: " + e.getLocalizedMessage());
            return new ModelAndView("crashes").addObject("crashes", crashManager.getCrashes(true));
        }
    }

    @RequestMapping(value = "/crashview", method = RequestMethod.GET)
    public ModelAndView viewCrash(HttpServletRequest request) throws Exception {
        try {
            ModelAndView mav = new ModelAndView("crashview");
            Crash crash;
            String id = request.getParameter("id");
            crash = crashManager.getCrashForView(new Long(id));
            mav.addObject("crash", crash);
            mav.addAllObjects(crashManager.getReferenceData());
            return mav;
        } catch (Exception e) {
            log.error("View crash failed: " + e.getLocalizedMessage());
            return showCrashes(request);
        }
    }

    @RequestMapping(value = "/crashremove", method = RequestMethod.GET)
    public ModelAndView removeCrash(HttpServletRequest request) throws Exception {
        try {
            String id = request.getParameter("id");
            crashManager.removeCrashById(new Long(id));
            saveMessage(request, "Crash was removed successfully");
        } catch (Exception e) {
            logException(request, e, "Remove crash failed");
        }
        return showCrashes(request);
    }

    @RequestMapping(value = "/crashrestore", method = RequestMethod.GET)
    public ModelAndView restoreCrash(HttpServletRequest request) throws Exception {
        try {
            String id = request.getParameter("id");
            crashManager.restoreCrashById(new Long(id));
            saveMessage(request, "Crash was restored successfully");
        } catch (Exception e) {
            logException(request, e, "Restore crash failed");
        }
        return showCrashes(request);
    }
}
