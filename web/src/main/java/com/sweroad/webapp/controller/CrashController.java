package com.sweroad.webapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sweroad.service.CrashManager;

@Controller
@RequestMapping("/crash*")
public class CrashController extends BaseFormController {

	@Autowired
	private CrashManager crashManager;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest() throws Exception {
		return new ModelAndView("crashes").addObject(crashManager.getAll());
	}

	@RequestMapping(value = "/crashexcel", method = RequestMethod.GET)
	public void generateExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String excelFile = getFilename(request);
			crashManager.generateCrashDataExcel(excelFile);
			downloadFile(response, excelFile);
		} catch (Exception e) {
			log.error("Error on export to excel: " + e.getLocalizedMessage());
		}
	}

	private String getFilename(HttpServletRequest request) {
		// the directory to upload to
		String uploadDir = getServletContext().getRealPath("/genexcel");
		// The following seems to happen when running jetty:run
		if (uploadDir == null) {
			uploadDir = new File("src/main/webapp/genexcel").getAbsolutePath();
		}
		uploadDir += "/" + request.getRemoteUser() + "/";
		// Create the directory if it doesn't exist
		File dirPath = new File(uploadDir);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		return uploadDir + "rcds_crashes_genby_" + request.getRemoteUser()
				+ ".xls";
	}

	private void downloadFile(HttpServletResponse response, String excelFile)
			throws URISyntaxException, IOException, FileNotFoundException {
		File f = new File(excelFile);
		String filename = excelFile.substring(excelFile.lastIndexOf('/') + 1);
		log.debug("Loading file " + excelFile + "(" + f.getAbsolutePath() + ")");
		if (f.exists()) {
			response.setContentType("application/vnd.ms-excel");
			response.setContentLength(new Long(f.length()).intValue());
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ filename + "\"");
			FileCopyUtils.copy(new FileInputStream(f),
					response.getOutputStream());
		} else {
			log.error("File" + excelFile + "(" + f.getAbsolutePath()
					+ ") does not exist");
		}
	}
}
