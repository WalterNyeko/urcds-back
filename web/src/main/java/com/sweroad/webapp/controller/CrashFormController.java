package com.sweroad.webapp.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.sweroad.model.Casualty;
import com.sweroad.model.CasualtyClass;
import com.sweroad.model.CasualtyType;
import com.sweroad.model.Crash;
import com.sweroad.model.Driver;
import com.sweroad.model.Vehicle;
import com.sweroad.model.VehicleType;
import com.sweroad.service.CrashManager;
import com.sweroad.service.GenericManager;
import com.sweroad.webapp.util.CrashFormHelper;

@Controller
@RequestMapping("/crashform*")
public class CrashFormController extends BaseFormController {

	@Autowired
	private CrashManager crashManager;
	@Autowired
	private GenericManager<VehicleType, Long> vehicleTypeManager;
	@Autowired
	private GenericManager<CasualtyClass, Long> casualtyClassManager;
	@Autowired
	private GenericManager<CasualtyType, Long> casualtyTypeManager;
	private final static Long DEFAULT_ID = 0L;

	public CrashFormController() {
		setCancelView("redirect:crashes");
		setSuccessView("redirect:crashes");
	}

	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView showForm(HttpServletRequest request)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		Crash crash;
		String id = request.getParameter("id");
		if (!StringUtils.isBlank(id)) {
			Long crashId = new Long(id);
			if (crashId > 0) {
				crash = getCrashById(request, crashId);
				request.getSession().setAttribute("crash", crash);
			} else {
				crash = (Crash) request.getSession().getAttribute("crash");
			}
		} else {
			crash = new Crash();
			crash.setId(DEFAULT_ID);
			if (request.getSession().getAttribute("crash") != null) {
				request.getSession().removeAttribute("crash");
			}
		}
		mav.addObject("crash", crash);
		mav.addAllObjects(crashManager.getReferenceData());
		return mav;
	}

	private Crash getCrashById(HttpServletRequest request, Long crashId) {
		Crash crash;
		String back = request.getParameter("back");
		if (!StringUtils.isBlank(back)) {
			Crash sessionCrash = (Crash) request.getSession().getAttribute(
					"crash");
			if (sessionCrash != null && sessionCrash.getId().equals(crashId)) {
				crash = sessionCrash;
			} else {
				crash = crashManager.get(crashId);
			}
		} else {
			crash = crashManager.get(crashId);
		}
		return crash;
	}

	@ModelAttribute
	@RequestMapping(value = "/crashform2", method = RequestMethod.POST)
	protected ModelAndView showForm2(Crash crash, BindingResult errors,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ModelAndView mav = new ModelAndView("crashform2");
		mergeWithSession(crash, request);
		request.getSession().setAttribute("crash", crash);
		mav.addObject("crash", crash);
		mav.addAllObjects(crashManager.getReferenceData());
		return mav;
	}

	private void mergeWithSession(Crash crash, HttpServletRequest request) {
		if (request.getSession().getAttribute("crash") != null
				&& crash.getId() != null) {
			Crash sessionCrash = (Crash) request.getSession().getAttribute(
					"crash");
			crash.setId(sessionCrash.getId());
			crash.setVehicles(sessionCrash.getVehicles());
			crash.setCasualties(sessionCrash.getCasualties());
			Collections.sort(crash.getVehicles());
		}
	}

	@ModelAttribute
	@RequestMapping(value = "/crashform2", method = RequestMethod.GET)
	protected ModelAndView crashForm2(HttpServletRequest request)
			throws Exception {

		ModelAndView mav = new ModelAndView("crashform2");
		Crash crash = (Crash) request.getSession().getAttribute("crash");
		if (crash == null) {
			crash = new Crash();
		} else {
			Collections.sort(crash.getVehicles());
		}
		mav.addObject("crash", crash);
		mav.addAllObjects(crashManager.getReferenceData());
		return mav;
	}

	@ModelAttribute
	@RequestMapping(value = "/crashformsubmit", method = RequestMethod.GET)
	public void onSubmit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Crash crash = (Crash) request.getSession().getAttribute("crash");
		if (crash.getCrashDateTimeString() != null
				&& !"".equals(crash.getCrashDateTimeString())) {
			crash.setCrashDateTime(parseDate(crash
					.getCrashDateTimeString()));
		}
		crashManager.saveCrash(crash);
		request.getSession().removeAttribute("crash");
		response.sendRedirect("/crashes");
	}
	
	private Date parseDate(String crashDateString) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"dd/MM/yyyy hh:mm");
		try{
			return formatter.parse(crashDateString);
		} catch (ParseException e) {
			try{
				return formatter.parse(crashDateString + " 12:00");
			} catch (Exception ex){
				return null;
			}
		} 		
	}

	@ModelAttribute
	@RequestMapping(value = "/crashformvehicle", method = RequestMethod.GET)
	protected ModelAndView vehicleForm(HttpServletRequest request)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		Vehicle vehicle = new Vehicle();
		String id = request.getParameter("id");
		Crash crash = (Crash) request.getSession().getAttribute("crash");

		if (!StringUtils.isBlank(id) && crash != null
				&& crash.getVehicles() != null) {
			vehicle = getVehicleFromSet(Long.parseLong(id), crash.getVehicles());
		}
		if (vehicle.getId() == null) {
			vehicle = new Vehicle();
			vehicle.setId(DEFAULT_ID);
			vehicle.setDriver(new Driver());
			vehicle.getDriver().setId(DEFAULT_ID);
		}
		mav.addObject("vehicle", vehicle);
		mav.addAllObjects(crashManager.getReferenceData());
		return mav;
	}

	@ModelAttribute
	@RequestMapping(value = "/crashformvehicle", method = RequestMethod.POST)
	protected ModelAndView vehicleForm(Vehicle vehicle, BindingResult errors,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Crash crash = (Crash) request.getSession().getAttribute("crash");
		if (vehicle.getId().equals(DEFAULT_ID)) {
			addVehicleToCrash(vehicle, crash);
		} else {
			updateCrashVehicle(vehicle, crash);
		}
		request.getSession().setAttribute("crash", crash);
		return showForm2(crash, errors, request, response);
	}

	@ModelAttribute
	@RequestMapping(value = "/crashformcasualty", method = RequestMethod.POST)
	protected ModelAndView casualtyForm(Casualty casualty,
			BindingResult errors, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Crash crash = (Crash) request.getSession().getAttribute("crash");
		if (casualty.getId().equals(DEFAULT_ID)) {
			addCasualtyToCrash(casualty, crash);
		} else {
			updateCrashCasualty(casualty, crash);
		}
		request.getSession().setAttribute("crash", crash);
		return showForm2(crash, errors, request, response);
	}

	@SuppressWarnings("rawtypes")
	@ModelAttribute
	@RequestMapping(value = "/crashformcasualty", method = RequestMethod.GET)
	protected ModelAndView casualtyForm(HttpServletRequest request)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		Casualty casualty = new Casualty();
		String id = request.getParameter("id");
		Crash crash = (Crash) request.getSession().getAttribute("crash");

		if (!StringUtils.isBlank(id) && crash != null
				&& crash.getVehicles() != null) {
			casualty = getCasualtyFromSet(Long.parseLong(id),
					crash.getCasualties());
		}
		if (casualty.getId() == null) {
			casualty = new Casualty();
			casualty.setId(DEFAULT_ID);
		}
		mav.addObject("casualty", casualty);
		Map<String, List> referenceData = crashManager.getReferenceData();
		referenceData.put("vehicles", crash.getVehicles());
		mav.addAllObjects(referenceData);
		return mav;
	}

	@ModelAttribute
	@RequestMapping(value = "/crashformcasualtydelete", method = RequestMethod.GET)
	protected ModelAndView deleteCasualty(HttpServletRequest request)
			throws Exception {
		String id = request.getParameter("id");
		if (!StringUtils.isBlank(id)) {
			Crash crash = (Crash) request.getSession().getAttribute("crash");
			crashManager.removeCasualtyFromCrash(crash, Long.parseLong(id));
			request.getSession().setAttribute("crash", crash);
		}
		return crashForm2(request);
	}

	@ModelAttribute
	@RequestMapping(value = "/crashformvehicledelete", method = RequestMethod.GET)
	protected ModelAndView deleteVehicle(HttpServletRequest request)
			throws Exception {
		String id = request.getParameter("id");
		if (!StringUtils.isBlank(id)) {
			Crash crash = (Crash) request.getSession().getAttribute("crash");
			crashManager.removeVehicleFromCrash(crash, Long.parseLong(id));
			CrashFormHelper.resetVehicleNumbers(crash);
			request.getSession().setAttribute("crash", crash);
		}
		return crashForm2(request);
	}

	private Vehicle getVehicleFromSet(long id, List<Vehicle> vehicles) {
		for (Vehicle vehicle : vehicles) {
			if (vehicle.getId().longValue() == id) {
				return vehicle;
			}
		}
		return null;
	}

	private Casualty getCasualtyFromSet(long id, List<Casualty> casualties) {
		for (Casualty casualty : casualties) {
			if (casualty.getId().longValue() == id) {
				return casualty;
			}
		}
		return null;
	}

	private void addVehicleToCrash(Vehicle vehicle, Crash crash) {
		if (CrashFormHelper.vehicleAlreadyExistsInList(crash.getVehicles(),
				vehicle)) {
			return;
		}
		long vehicleId = CrashFormHelper.getCrashsMaximumVehicleId(crash) + 1;
		int number = crash.getVehicles().size() + 1;
		vehicle.setId(vehicleId);
		vehicle.setNumber(number);

		if (vehicle.getDriver() != null) {
			vehicle.getDriver().setId(vehicle.getId());
			setDriverCasualtyType(vehicle.getDriver());
		}
		setVehicleType(vehicle);
		crash.addVehicle(vehicle);
	}

	private void updateCrashVehicle(Vehicle vehicle, Crash crash) {
		setDriverCasualtyType(vehicle.getDriver());
		setVehicleType(vehicle);
		List<Vehicle> crashVehicles = crash.getVehicles();
		for (int i = 0; i < crashVehicles.size(); i++) {
			if (crashVehicles.get(i).getId().equals(vehicle.getId())) {
				crashVehicles.set(i, vehicle);
			}
		}
		crash.setVehicles(crashVehicles);
	}

	private void addCasualtyToCrash(Casualty casualty, Crash crash) {
		if (CrashFormHelper.casualtyAlreadyExistsInList(crash.getCasualties(),
				casualty)) {
			return;
		}
		long id = CrashFormHelper.getCrashsMaximumCasualtyId(crash) + 1;
		casualty.setId(id);
		setCasualtyParams(casualty, crash);
		crash.addCasualty(casualty);
	}

	private void updateCrashCasualty(Casualty casualty, Crash crash) {
		setCasualtyParams(casualty, crash);
		List<Casualty> crashCasualties = crash.getCasualties();
		for (int i = 0; i < crashCasualties.size(); i++) {
			if (crashCasualties.get(i).getId().equals(casualty.getId())) {
				crashCasualties.set(i, casualty);
			}
		}
		crash.setCasualties(crashCasualties);
	}

	private void setVehicleType(Vehicle vehicle) {
		if (vehicle.getVehicleType() != null) {
			VehicleType vt = vehicleTypeManager.get(vehicle.getVehicleType()
					.getId());
			vehicle.setVehicleType(vt);
		}
	}

	private void setDriverCasualtyType(Driver driver) {
		if (driver.getCasualtyType() != null) {
			CasualtyType casualtyType = casualtyTypeManager.get(driver
					.getCasualtyType().getId());
			driver.setCasualtyType(casualtyType);
		}
	}

	private void setCasualtyParams(Casualty casualty, Crash crash) {
		if (casualty.getCasualtyClass() != null) {
			CasualtyClass casualtyClass = casualtyClassManager.get(casualty
					.getCasualtyClass().getId());
			casualty.setCasualtyClass(casualtyClass);
		}
		if (casualty.getCasualtyType() != null) {
			CasualtyType casualtyType = casualtyTypeManager.get(casualty
					.getCasualtyType().getId());
			casualty.setCasualtyType(casualtyType);
		}
		if (casualty.getVehicle() != null
				&& casualty.getVehicle().getId() != null) {
			for (Vehicle vehicle : crash.getVehicles()) {
				if (casualty.getVehicle().getId().equals(vehicle.getId())) {
					casualty.setVehicle(vehicle);
				}
			}
		} else {
			casualty.setVehicle(null);
		}
	}
}
