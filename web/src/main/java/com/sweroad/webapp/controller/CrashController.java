package com.sweroad.webapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			crash = crashManager.get(new Long(id));
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
