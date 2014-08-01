package com.sweroad.webapp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sweroad.model.Crash;
import com.sweroad.model.Vehicle;
import com.sweroad.model.VehicleType;
import com.sweroad.service.CrashManager;
import com.sweroad.service.GenericManager;

@Controller
@RequestMapping("/crashform*")
public class CrashFormController extends BaseFormController {
	
	@Autowired
	private CrashManager crashManager;	
	@Autowired
	private GenericManager<VehicleType, Long> vehicleTypeManager;
	
	public CrashFormController() {
		setCancelView("redirect:crashes");
		setSuccessView("redirect:crashes");
	}

	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView showForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		Crash crash;
		String id = request.getParameter("id");
		if(!StringUtils.isBlank(id)) {
			crash = crashManager.get(new Long(id));
			request.getSession().setAttribute("crash", crash);
		} else {
			crash = new Crash();
		}
		mav.addObject("crash", crash);
		mav.addAllObjects(crashManager.getReferenceData());
		return mav;
	}
	
	@ModelAttribute
	@RequestMapping(value = "/crashform2", method = RequestMethod.POST)
	protected ModelAndView showForm2(Crash crash, BindingResult errors,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		ModelAndView mav = new ModelAndView("crashform2");
		mergeWithSession(crash, request);
		request.getSession().setAttribute("crash", crash);
		mav.addObject("crash", crash);
		mav.addAllObjects(crashManager.getReferenceData());
		return mav;
	}

	private void mergeWithSession(Crash crash, HttpServletRequest request) {
		if (request.getSession().getAttribute("crash") != null) {
			Crash sessionCrash = (Crash) request.getSession().getAttribute("crash");
			crash.setId(sessionCrash.getId());
			crash.setVehicles(sessionCrash.getVehicles());
			crash.setCasualties(sessionCrash.getCasualties());
		}
	}
	
	@ModelAttribute
	@RequestMapping(value = "/crashform2", method = RequestMethod.GET)
	protected ModelAndView crashForm2(HttpServletRequest request) throws Exception {
	
		ModelAndView mav = new ModelAndView("crashform2");
		Crash crash = (Crash) request.getSession().getAttribute("crash");
		if (crash == null) {
			crash = new Crash();
		}
		mav.addObject("crash", crash);
		mav.addAllObjects(crashManager.getReferenceData());
		return mav;
	}

	@ModelAttribute
	@RequestMapping(value = "/crashformsubmit", method = RequestMethod.GET)
	public void onSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Crash crash = (Crash) request.getSession().getAttribute("crash");
		crash.setCrashDateTime(new Date());
		crashManager.saveCrash(crash);
		request.getSession().removeAttribute("crash");
	    response.sendRedirect("/crashes");
	}
	
	@ModelAttribute
	@RequestMapping(value = "/crashformvehicle", method = RequestMethod.GET)
	protected ModelAndView vehicleForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		Vehicle vehicle;
		String id = request.getParameter("id");
		Crash crash = (Crash) request.getSession().getAttribute("crash");
		
		if(!StringUtils.isBlank(id) && crash != null && crash.getVehicles() != null) {
			vehicle = getVehicleFromSet(Long.parseLong(id), crash.getVehicles());
			if (vehicle == null) {
				vehicle = new Vehicle();
			}
		} else {
			vehicle = new Vehicle();
		}
		mav.addObject("vehicle", vehicle);
		mav.addAllObjects(crashManager.getReferenceData());
		return mav;
	}
	
	@ModelAttribute
	@RequestMapping(value = "/crashformvehicle", method = RequestMethod.POST)
	protected ModelAndView vehicleForm(Vehicle vehicle, BindingResult errors,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Crash crash = (Crash) request.getSession().getAttribute("crash");
		addVehicleToCrash(vehicle, crash);
		request.getSession().setAttribute("crash", crash);
		return showForm2(crash, errors, request, response);
	}
	
	private Vehicle getVehicleFromSet(long id, List<Vehicle> vehicles) {
		for(Vehicle vehicle : vehicles) {
			if (vehicle.getId().longValue() == id) {
				return vehicle;
			}
		}
		return null;
	}
	
	private void addVehicleToCrash(Vehicle vehicle, Crash crash) {
		if (crash.getVehicles() == null || crash.getVehicleCount() == 0) {
			crash.setVehicles(new ArrayList<Vehicle>());
			vehicle.setId(new Long(1));
			vehicle.setNumber(1);
		} else {
			long vehicleId = crash.getVehicles().size() + 1;
			vehicle.setId(vehicleId);	
			vehicle.setNumber((int)vehicleId);
		}
		if (vehicle.getDriver() != null) {
			vehicle.getDriver().setId(vehicle.getId());
		}	
		setVehicleType(vehicle);
		crash.addVehicle(vehicle);
	}
	
	private void setVehicleType(Vehicle vehicle) {
		if (vehicle.getVehicleType() != null) {
			VehicleType vt = vehicleTypeManager.get(vehicle.getVehicleType().getId());
			vehicle.setVehicleType(vt);
		}
	}
}
