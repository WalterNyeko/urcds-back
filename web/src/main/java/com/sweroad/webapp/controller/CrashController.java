package com.sweroad.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sweroad.model.Crash;
import com.sweroad.service.CrashManager;
import com.sweroad.service.GenericManager;

@Controller
@RequestMapping("/crash*")
public class CrashController {

	@Autowired
	private CrashManager crashManager;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest() throws Exception {
		return new ModelAndView("crashes").addObject(crashManager.getAll());
	}
	
	@RequestMapping(value = "/crashexcel", method = RequestMethod.GET)
	public ModelAndView generateExcel() throws Exception {
		String excelFile = crashManager.generateCrashDataExcel();
		return handleRequest();
	}

}
