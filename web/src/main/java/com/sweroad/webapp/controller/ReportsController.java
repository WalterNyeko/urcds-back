package com.sweroad.webapp.controller;

import com.mysql.jdbc.StringUtils;
import com.sweroad.model.Crash;
import com.sweroad.reporting.builder.ReportBuilder;
import com.sweroad.service.CrashManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by Frank on 3/18/15.
 */
@Controller
@RequestMapping("/reports*")
public class ReportsController extends BaseFormController {

    @Autowired
    private ReportBuilder reportBuilder;
    @Autowired
    private CrashManager crashManager;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showReports() throws Exception {
        ModelAndView mav = new ModelAndView("reports/reports");
        return mav;
    }

    @RequestMapping(value = "/reportsgen", method = RequestMethod.GET)
    public ResponseEntity<byte[]> generateReport(HttpServletRequest request) throws Exception {
        String reportFilePath = buildReport(request, crashManager.getAvailableCrashes(false));
        File reportFile = new File(reportFilePath);
        FileInputStream fileInputStream = new FileInputStream(reportFile);
        byte[] contents = new byte[(int) reportFile.length()];
        while((fileInputStream.read(contents)) != -1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("content-disposition", "inline;filename=" + reportFilePath
                .substring(reportFilePath.lastIndexOf(File.separator) + 1, reportFilePath.length()));
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
        return response;
    }

    private String getReportFileName(HttpServletRequest request) {
        String reportName = request.getParameter("reportName");
        String reportsDir = getReportsDirectory(request.getRemoteUser());
        return reportsDir.concat(File.separator).concat(reportName).concat(".pdf");
    }

    private String getReportsDirectory(String username) {
        String resourcesDir = getServletContext().getRealPath("/resources");
        if (resourcesDir == null) {
            resourcesDir = new File("src/main/webapp/resources").getAbsolutePath();
        }
        String reportsDirPath = resourcesDir.concat(File.separator).concat("reports").concat(File.separator).concat(username);
        File reportsDir = new File(reportsDirPath);
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }
        return reportsDirPath;
    }

    private String buildReport(HttpServletRequest request, List<Crash> crashes) {
        String reportFileName = getReportFileName(request);
        String reportName = request.getParameter("reportName");
        if (StringUtils.isNullOrEmpty(reportName))
            return null;
        if (reportName.equalsIgnoreCase("CollisionTypeByCrashSeverity")) {
            reportBuilder.buildCollisionTypeBySeverityReport(crashes, reportFileName);
        } else if (reportName.equalsIgnoreCase("CrashCauseByCrashSeverity")) {
            reportBuilder.buildCrashCauseBySeverityReport(crashes, reportFileName);
        } else if (reportName.equalsIgnoreCase("DistrictByCrashSeverity")) {
            reportBuilder.buildDistrictBySeverityReport(crashes, reportFileName);
        } else if (reportName.equalsIgnoreCase("JunctionTypeByCrashSeverity")) {
            reportBuilder.buildJunctionTypeBySeverityReport(crashes, reportFileName);
        } else if (reportName.equalsIgnoreCase("PoliceStationByCrashSeverity")) {
            reportBuilder.buildPoliceStationBySeverityReport(crashes, reportFileName);
        } else if (reportName.equalsIgnoreCase("RoadSurfaceByCrashSeverity")) {
            reportBuilder.buildRoadSurfaceBySeverityReport(crashes, reportFileName);
        } else if (reportName.equalsIgnoreCase("RoadwayCharacterByCrashSeverity")) {
            reportBuilder.buildRoadwayCharacterBySeverityReport(crashes, reportFileName);
        } else if (reportName.equalsIgnoreCase("SurfaceConditionByCrashSeverity")) {
            reportBuilder.buildSurfaceConditionBySeverityReport(crashes, reportFileName);
        } else if (reportName.equalsIgnoreCase("SurfaceTypeByCrashSeverity")) {
            reportBuilder.buildSurfaceTypeBySeverityReport(crashes, reportFileName);
        } else if (reportName.equalsIgnoreCase("TimeRangeByCrashSeverity")) {
            reportBuilder.buildTimeRangeBySeverityReport(crashes, reportFileName);
        } else if (reportName.equalsIgnoreCase("VehicleFailureTypeByCrashSeverity")) {
            reportBuilder.buildVehicleFailureTypeBySeverityReport(crashes, reportFileName);
        } else if (reportName.equalsIgnoreCase("VehicleTypeByCrashSeverity")) {
            reportBuilder.buildVehicleTypeBySeverityReport(crashes, reportFileName);
        } else if (reportName.equalsIgnoreCase("CasualtyAgeByCrashSeverity")) {
            reportBuilder.buildCasualtyAgeGenderBySeverityReport(crashes, reportFileName);
        } else if (reportName.equalsIgnoreCase("WeatherByCrashSeverity")) {
            reportBuilder.buildWeatherBySeverityReport(crashes, reportFileName);
        }
        return reportFileName;
    }
}
