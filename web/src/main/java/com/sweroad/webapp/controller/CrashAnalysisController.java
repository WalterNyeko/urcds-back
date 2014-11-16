package com.sweroad.webapp.controller;

import com.sweroad.model.Crash;
import com.sweroad.model.SearchCriteria;
import com.sweroad.service.CrashManager;
import com.sweroad.service.GenericManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

@Controller
@RequestMapping("/analysis*")
public class CrashAnalysisController extends BaseFormController {

    @Autowired
    private CrashManager crashManager;

    @Autowired
    private GenericManager<Crash, Long> genericCrashManager;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showCrashes() throws Exception {
        ModelAndView mav = new ModelAndView("analysis/crashanalysis");
        mav.addObject(crashManager.getCrashes());
        return mav;
    }

    @RequestMapping(value = "/analysiscrashselect", method = RequestMethod.GET)
    public ModelAndView selectCrash(HttpServletRequest request) throws Exception {
        try {
            ModelAndView mav = new ModelAndView("analysis/selectcrash");
            SearchCriteria criteria = new SearchCriteria();
            mav.addObject("criteria", criteria);
            mav.addAllObjects(crashManager.getOrderedRefData());
            return mav;
        } catch (Exception e) {
            log.error("Select crash failed: " + e.getLocalizedMessage());
            return showCrashes();
        }
    }

    @RequestMapping(value = "/crashremove", method = RequestMethod.GET)
    public ModelAndView removeCrash(HttpServletRequest request) throws Exception {
        try {
            String id = request.getParameter("id");
            crashManager.removeCrashById(new Long(id));
            return showCrashes();
        } catch (Exception e) {
            log.error("Remove crash failed: " + e.getLocalizedMessage());
            return showCrashes();
        }
    }

    @RequestMapping(value = "/crashrestore", method = RequestMethod.GET)
    public ModelAndView restoreCrash(HttpServletRequest request) throws Exception {
        try {
            String id = request.getParameter("id");
            crashManager.restoreCrashById(new Long(id));
            return showCrashes();
        } catch (Exception e) {
            log.error("Restore crash failed: " + e.getLocalizedMessage());
            return showCrashes();
        }
    }

    @RequestMapping(value = "/crashexcel", method = RequestMethod.GET)
    public void generateExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String excelFile = getFilename(request);
            crashManager.generateCrashDataExcel(excelFile);
            downloadFile(response, excelFile);
        } catch (Exception e) {
            log.error("Error on export to excel: " + e.getLocalizedMessage());
        }
    }

    private String getFilename(HttpServletRequest request) {
        String uploadDir = getServletContext().getRealPath("/genexcel");
        if (uploadDir == null) {
            uploadDir = new File("src/main/webapp/genexcel").getAbsolutePath();
        }
        uploadDir += "/" + request.getRemoteUser() + "/";
        File dirPath = new File(uploadDir);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        return uploadDir + "rcds_crashes_genby_" + request.getRemoteUser() + ".xlsx";
    }

    private void downloadFile(HttpServletResponse response, String excelFile) throws URISyntaxException, IOException,
            FileNotFoundException {
        File f = new File(excelFile);
        String filename = excelFile.substring(excelFile.lastIndexOf('/') + 1);
        log.debug("Loading file " + excelFile + "(" + f.getAbsolutePath() + ")");
        if (f.exists()) {
            response.setContentType("application/vnd.ms-excel");
            response.setContentLength(new Long(f.length()).intValue());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            FileCopyUtils.copy(new FileInputStream(f), response.getOutputStream());
        } else {
            log.error("File" + excelFile + "(" + f.getAbsolutePath() + ") does not exist");
        }
    }
}
