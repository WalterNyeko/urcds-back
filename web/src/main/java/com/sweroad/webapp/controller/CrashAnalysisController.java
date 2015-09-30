package com.sweroad.webapp.controller;

import com.mysql.jdbc.StringUtils;
import com.sweroad.model.*;
import com.sweroad.service.CasualtyManager;
import com.sweroad.service.CrashManager;
import com.sweroad.service.SearchCriteriaManager;
import com.sweroad.service.VehicleManager;
import com.sweroad.util.DateUtil;
import com.sweroad.webapp.util.CrashAnalysisHelper;
import com.sweroad.webapp.util.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/analysis*/**")
public class CrashAnalysisController extends BaseFormController {

    @Autowired
    private CasualtyManager casualtyManager;
    @Autowired
    private CrashManager crashManager;
    @Autowired
    private SearchCriteriaManager searchCriteriaManager;
    @Autowired
    private VehicleManager vehicleManager;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showCrashes(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("analysis/crashanalysis");
        List<Crash> crashes = getCrashes(request);
        mav.addObject(crashes);
        return mav;
    }

    @RequestMapping(value = "/analysisvehicles", method = RequestMethod.GET)
    public ModelAndView showVehicles(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("analysis/vehicleanalysis");
        mav.addObject("vehicles", getVehicles(request));
        return mav;
    }

    @RequestMapping(value = "/analysiscasualties", method = RequestMethod.GET)
    public ModelAndView showCasualties(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("analysis/casualtyanalysis");
        mav.addObject("casualties", getCasualties(request));
        return mav;
    }

    @RequestMapping(value="/analysisgisselect", method = RequestMethod.POST)
    public ModelAndView showGisSelectedCrashes(HttpServletRequest request, @RequestParam("crashIds") String crashIds) throws Exception {
        List<Long> ids = new ArrayList<Long>();
        for(String crashId : crashIds.split(",")) {
            ids.add(Long.parseLong(crashId));
        }
        setCrashesInSession(request, crashManager.getCrashes(ids));
        return showCrashes(request);
    }

    private List<Crash> getCrashes(HttpServletRequest request) throws Exception {
        List<Crash> crashes = (List<Crash>) request.getSession().getAttribute("crashes");
        if (crashes != null) {
            return crashes;
        } else {
            crashes = crashManager.getAvailableCrashes();
            setCrashesInSession(request, crashes);
            return crashes;
        }
    }

    private void setCrashesInSession(HttpServletRequest request, List<Crash> crashes) throws ParseException {
        SessionHelper.crashesToJsonAndSetInSession(request, crashes);
        SessionHelper.crashAttributesToJsonAndSetInSession(request, crashManager.getOrderedRefData());
    }

    private List<Vehicle> getVehicles(HttpServletRequest request) throws Exception {
        List<Crash> crashes = getCrashes(request);
        return vehicleManager.extractVehiclesFromCrashList(crashes);
    }

    private List<Casualty> getCasualties(HttpServletRequest request) throws Exception {
        List<Crash> crashes = getCrashes(request);
        return casualtyManager.extractCasualtiesFromCrashList(crashes);
    }

    @RequestMapping(value = "/analysiscrashselect", method = RequestMethod.GET)
    public ModelAndView selectCrash(HttpServletRequest request) throws Exception {
        try {
            ModelAndView mav = new ModelAndView("analysis/selectcrash");
            SearchCriteria criteria = new SearchCriteria();
            mav.addObject("criteria", criteria);
            mav.addAllObjects(crashManager.getOrderedRefData());
            mav.addObject("years", CrashAnalysisHelper.getYearsForSearch());
            mav.addObject("months", CrashAnalysisHelper.getMonthsForSearch(request));
            SessionHelper.policeStationsToJsonAndSetInAttribute(request, (List<PoliceStation>) mav.getModelMap().get("policeStations"));
            return mav;
        } catch (Exception e) {
            log.error("Select crash failed: " + e.getLocalizedMessage());
            return showCrashes(request);
        }
    }

    @RequestMapping(value = "/analysiscrashselect", method = RequestMethod.POST)
    public ModelAndView selectCrash(SearchCriteria criteria, HttpServletRequest request) throws Exception {
        try {
            processCriteria(criteria);
            List<Crash> crashes = searchCriteriaManager.getCrashesByCriteria(criteria);
            setCrashesInSession(request, crashes);
        } catch(ParseException e) {
            logException(request, e, "Date provided was in wrong format.");
        } catch (Exception e) {
            logException(request, e, "Select crashes failed. Please contact your System Administrator.");
        }
        return showCrashes(request);
    }

    private void processCriteria(SearchCriteria criteria) throws ParseException {
        if (!StringUtils.isNullOrEmpty(criteria.getStartDateString())) {
            criteria.setStartDate(DateUtil.convertStringToDate(criteria.getStartDateString()));
        }
        if (!StringUtils.isNullOrEmpty(criteria.getEndDateString())) {
            criteria.setEndDate(DateUtil.convertStringToDate(criteria.getEndDateString()));
        }
    }

    @RequestMapping(value = "/analysisdownloadexcel", method = RequestMethod.GET)
    public void generateExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String excelFile = getFilename(request);
            crashManager.generateCrashDataExcel(getCrashes(request), excelFile);
            downloadFile(response, excelFile);
        } catch (Exception e) {
            logException(request, e, "Data export to Excel failed. Please contact your System Administrator.");
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

    private void downloadFile(HttpServletResponse response, String excelFile) throws URISyntaxException, IOException {
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

    @RequestMapping(value = {"/analysis/crashstats", "/analysis/crosstabs", "/analysis/crashtrends"}, method = RequestMethod.GET)
    public ModelAndView showAnalysis() {
        return new ModelAndView("analysis/analysis");
    }
}
