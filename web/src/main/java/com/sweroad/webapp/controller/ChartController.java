package com.sweroad.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sweroad.service.CrashService;
import com.sweroad.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sweroad.model.Crash;
import com.sweroad.model.CrashCause;
import com.sweroad.model.CrashSeverity;
import com.sweroad.service.GenericService;

@Controller
@RequestMapping("/crashchart*")
public class ChartController extends BaseFormController {

    @Autowired
    private CrashService crashService;
    @Autowired
    private GenericService<CrashSeverity, Long> crashSeverityService;
    @Autowired
    private GenericService<CrashCause, Long> crashCauseService;

    @RequestMapping(value = "/crashchartseverity", method = RequestMethod.GET)
    public
    @ResponseBody
    String getCrashSeverityPieChart()
            throws Exception {
        List<Crash> crashes = crashService.getAvailableCrashes(false);
        String chart = "[{\"type\":\"pie\",\"name\":\"Crash Severity Pie Chart\",";
        chart +=  "\"crashCount\" : " + crashes.size() + ",";
        chart += constructCrashChartData(crashes);
        chart += "}]";
        return chart;
    }

    @RequestMapping(value = "/crashchartcause", method = RequestMethod.GET)
    public
    @ResponseBody
    String getCrashCausePieChart() throws Exception {
        String chart = "[{\"type\":\"pie\",\"name\":\"Crash Cause Pie Chart\",";
        chart += constructCrashCauseChartData();
        chart += "}]";
        return chart;
    }

    @RequestMapping(value = "/crashchartdistrctmonthly", method = RequestMethod.GET)
    public
    @ResponseBody
    String getCrashDistricMonthlyNumbers() throws Exception {
        String chart = "[{\"type\":\"pie\",\"name\":\"Crash Cause Pie Chart\",";
        chart += constructCrashCauseChartData();
        chart += "}]";
        return chart;
    }

    private String constructCrashChartData(List<Crash> crashes) {
        String data = "\"data\":[";
        Map<String, Integer> severityNumbers = getCrashSeverityNumbers(crashes);
        for (Map.Entry<String, Integer> entry : severityNumbers.entrySet()) {
            data += "[\"" + entry.getKey() + "\", " + entry.getValue() + "],";
        }
        data = data.substring(0, data.length() - 1);
        data += "]";
        return data;
    }

    private Map<String, Integer> getCrashSeverityNumbers(List<Crash> crashes) {
        List<CrashSeverity> severities = crashSeverityService.getAllDistinct();
        Map<String, Integer> severityNumbers = new HashMap<String, Integer>();
        for (CrashSeverity severity : severities) {
            severityNumbers.put(severity.getName(),
                    getNumberOfCrashesBySeverityId(crashes, severity.getId()));
        }
        return severityNumbers;
    }

    private Integer getNumberOfCrashesBySeverityId(List<Crash> crashes, Long id) {
        int count = 0;
        for (Crash crash : crashes) {
            if (crash.getCrashSeverity() != null
                    && crash.getCrashSeverity().getId().equals(id)) {
                count++;
            }
        }
        return count;
    }

    private String constructCrashCauseChartData() {
        String data = "\"data\":[";
        Map<String, Integer> causeNumbers = getCrashCauseNumbers();
        int count = 1;
        int sum = 0;
        for (Map.Entry<String, Integer> entry : causeNumbers.entrySet()) {
            if (count++ < 8) {
                data += "[\"" + entry.getKey() + "\", " + entry.getValue() + "],";
            } else {
                sum += entry.getValue();
            }
        }
        if (sum > 0) {
            data += "[\" Others \", " + sum + "]]";
        } else {
            data = data.substring(0, data.length() - 1);
            data += "]";
        }
        return data;
    }

    private Map<String, Integer> getCrashCauseNumbers() {
        List<CrashCause> causes = crashCauseService.getAllDistinct();
        List<Crash> crashes = crashService.getAvailableCrashes(false);
        Map<String, Integer> causeNumbers = new HashMap<String, Integer>();
        for (CrashCause cause : causes) {
            int count = getNumberOfCrashesByCauseId(crashes, cause.getId());
            if (count == 0) {
                continue;
            }
            causeNumbers.put(cause.getName(), count);
        }
        causeNumbers = MapUtil.reverseSortByValue(causeNumbers);
        return causeNumbers;
    }

    private Integer getNumberOfCrashesByCauseId(List<Crash> crashes, Long id) {
        int count = 0;
        for (Crash crash : crashes) {
            if (crash.getCrashCause() != null
                    && crash.getCrashCause().getId().equals(id)) {
                count++;
            }
        }
        return count;
    }
}
