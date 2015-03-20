package com.sweroad.webapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.StringUtils;
import com.sweroad.service.GenericManager;
import com.sweroad.util.GisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
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

    @Autowired
    private GenericManager<Crash, Long> genericCrashManager;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showCrashes() throws Exception {
        return new ModelAndView("crashes").addObject(crashManager.getCrashes());
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
            return showCrashes();
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
        return showCrashes();
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
        return showCrashes();
    }
}
