package com.sweroad.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sweroad.model.Crash;
import com.sweroad.model.CrashCause;
import com.sweroad.model.CrashSeverity;
import com.sweroad.service.CrashManager;
import com.sweroad.service.GenericManager;

@Controller
@RequestMapping("/crashchart*")
public class ChartController extends BaseFormController {

	@Autowired
	private CrashManager crashManager;
	@Autowired
	private GenericManager<CrashSeverity, Long> crashSeverityManager;
	@Autowired
	private GenericManager<CrashCause, Long> crashCauseManager;

	@RequestMapping(value = "/crashchartseverity", method = RequestMethod.GET)
	public @ResponseBody
	String getCrashSeverityPieChart(HttpServletRequest request)
			throws Exception {
		String chart = "[{\"type\":\"pie\",\"name\":\"Crash Severity Pie Chart\",";
		chart += constructCrashChartData();
		chart += "}]";
		return chart;
	}

	@RequestMapping(value = "/crashchartcause", method = RequestMethod.GET)
	public @ResponseBody
	String getCrashCausePieChart(HttpServletRequest request) throws Exception {
		String chart = "[{\"type\":\"pie\",\"name\":\"Crash Cause Pie Chart\",";
		chart += constructCrashCauseChartData();
		chart += "}]";
		return chart;
	}

    @RequestMapping(value = "/crashchartdistrctmonthly", method = RequestMethod.GET)
    public @ResponseBody
    String getCrashDistricMonthlyNumbers(HttpServletRequest request) throws Exception {
        String chart = "[{\"type\":\"pie\",\"name\":\"Crash Cause Pie Chart\",";
        chart += constructCrashCauseChartData();
        chart += "}]";
        return chart;
    }

	private String constructCrashChartData() {
		String data = "\"data\":[";
		Map<String, Integer> severityNumbers = getCrashSeverityNumbers();
		for (Map.Entry<String, Integer> entry : severityNumbers.entrySet()) {
			data += "[\"" + entry.getKey() + "\", " + entry.getValue() + "],";
		}
		data = data.substring(0, data.length() - 1);
		data += "]";
		return data;
	}

	private Map<String, Integer> getCrashSeverityNumbers() {
		List<CrashSeverity> severities = crashSeverityManager.getAllDistinct();
		List<Crash> crashes = crashManager.getAllDistinct();
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
		for (Map.Entry<String, Integer> entry : causeNumbers.entrySet()) {
			data += "[\"" + entry.getKey() + "\", " + entry.getValue() + "],";
		}
		data = data.substring(0, data.length() - 1);
		data += "]";
		return data;
	}

	private Map<String, Integer> getCrashCauseNumbers() {
		List<CrashCause> causes = crashCauseManager.getAllDistinct();
		List<Crash> crashes = crashManager.getAllDistinct();
		Map<String, Integer> causeNumbers = new HashMap<String, Integer>();
		for (CrashCause cause : causes) {
			int count = getNumberOfCrashesByCauseId(crashes, cause.getId());
			if (count == 0) {
				continue;
			}
			causeNumbers.put(cause.getName(), count);
		}
		return causeNumbers;
	}

	private Integer getNumberOfCrashesByCauseId(List<Crash> crashes, Long id) {
		int count = 0;
		for (Crash crash : crashes) {
			if (crash.getMainCrashCause() != null
					&& crash.getMainCrashCause().getId().equals(id)) {
				count++;
			}
		}
		return count;
	}
}
