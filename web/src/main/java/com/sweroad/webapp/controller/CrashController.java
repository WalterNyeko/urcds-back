package com.sweroad.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sweroad.model.Crash;
import com.sweroad.service.GenericManager;

@Controller
@RequestMapping("/crash*")
public class CrashController {

	private GenericManager<Crash, Long> crashManager;
	
	@Autowired
	public void setCrashManager(@Qualifier("crashManager") GenericManager<Crash, Long> crashManager) {
		this.crashManager = crashManager;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest() throws Exception {
		return new ModelAndView().addObject(crashManager.getAll());
	}

}
