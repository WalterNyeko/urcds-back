package com.sweroad.webapp.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sweroad.model.*;
import com.sweroad.service.CrashService;
import com.sweroad.service.GenericService;
import com.sweroad.util.DateUtil;
import com.sweroad.webapp.util.SessionHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sweroad.webapp.util.CrashFormHelper;

@Controller
@RequestMapping("/crashform*")
public class CrashFormController extends BaseFormController {

    @Autowired
    private CrashService crashService;
    @Autowired
    private GenericService<VehicleType, Long> vehicleTypeService;
    @Autowired
    private GenericService<CasualtyClass, Long> casualtyClassService;
    @Autowired
    private GenericService<CasualtyType, Long> casualtyTypeService;
    @Autowired
    private GenericService<PoliceStation, Long> policeStationService;
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
            crash = getCrashById(request, crashId);
            request.getSession().setAttribute("crash", crash);
        } else {
            crash = new Crash();
            crash.setId(DEFAULT_ID);
            if (request.getSession().getAttribute("crash") != null) {
                request.getSession().removeAttribute("crash");
            }
        }
        mav.addObject("crash", crash);
        mav.addAllObjects(crashService.getReferenceData());
        SessionHelper.persistPoliceStationsInSession(request, (List<PoliceStation>) mav.getModelMap().get("policeStations"));
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
                crash.setPoliceStation(policeStationService.get(crash.getPoliceStation().getId()));
            } else {
                crash = crashService.get(crashId);
            }
        } else {
            crash = crashService.get(crashId);
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
        if (!StringUtils.isBlank(request.getParameter("shouldSave"))) {
            onSubmit(crash, request, response);
            return null;
        }
        mav.addObject("crash", crash);
        mav.addAllObjects(crashService.getReferenceData());
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
            crash.setDateCreated(sessionCrash.getDateCreated());
            crash.setCreatedBy(sessionCrash.getCreatedBy());
            crash.setReportingDate(sessionCrash.getReportingDate());
            crash.setSupervisingDate(sessionCrash.getSupervisingDate());
            crash.setReportingOfficerName(sessionCrash.getReportingOfficerName());
            crash.setReportingOfficerRank(sessionCrash.getReportingOfficerRank());
            crash.setSupervisingOfficerName(sessionCrash.getSupervisingOfficerName());
            crash.setSupervisingOfficerRank(sessionCrash.getSupervisingOfficerRank());
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
        mav.addAllObjects(crashService.getReferenceData());
        return mav;
    }

    @ModelAttribute
    @RequestMapping(value = "/crashformsubmit", method = RequestMethod.POST)
    public void onSubmit(Crash crash, HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        Crash sessionCrash = (Crash) request.getSession().getAttribute("crash");
        if (sessionCrash != null && sessionCrash.getCrashDateTimeString() != null
                && !"".equals(sessionCrash.getCrashDateTimeString())) {
            sessionCrash.setCrashDateTime(DateUtil.parseDate("yyyy-MM-dd hh:mm", sessionCrash
                    .getCrashDateTimeString()));
        }
        sessionCrash.setReportingDate(crash.getReportingDate());
        sessionCrash.setSupervisingDate(crash.getSupervisingDate());
        sessionCrash.setReportingOfficerName(crash.getReportingOfficerName());
        sessionCrash.setReportingOfficerRank(crash.getReportingOfficerRank());
        sessionCrash.setSupervisingOfficerName(crash.getSupervisingOfficerName());
        sessionCrash.setSupervisingOfficerRank(crash.getSupervisingOfficerRank());
        try {
            String successMessage = getCrashSaveMessage(sessionCrash);
            crashService.saveCrash(sessionCrash);
            saveMessage(request, successMessage);
        } catch (Exception e) {
            logException(request, e, "FAILURE: Crash " + sessionCrash.getTarNo() + " failed to save. Please contact your System Administrator.");
        }
        request.getSession().removeAttribute("crash");
        response.sendRedirect(request.getContextPath() + "/crashes");
    }

    private String getCrashSaveMessage(Crash crash) {
        String savedOrUpdated = crash.getId().equals(DEFAULT_ID) ? "saved" : "updated";
        return "Crash " + crash.getTarNo() + " was " + savedOrUpdated + " successfully.";
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
        mav.addAllObjects(crashService.getReferenceData());
        return mav;
    }

    @ModelAttribute
    @RequestMapping(value = "/crashformvehicle", method = RequestMethod.POST)
    protected ModelAndView vehicleForm(Vehicle vehicle, BindingResult errors,
                                       HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Crash crash = (Crash) request.getSession().getAttribute("crash");
        try {
            if (vehicle.getId().equals(DEFAULT_ID)) {
                addVehicleToCrash(vehicle, crash);
                saveMessage(request, "Vehicle " + vehicle.getNumber() + " added successfully.");
            } else {
                updateCrashVehicle(vehicle, crash);
                saveMessage(request, "Vehicle " + vehicle.getNumber() + " updated successfully.");
            }
            crash.setDirty(true);
        } catch (Exception e) {
            logException(request, e, "FAILURE: Vehicle " + vehicle.getNumber() + " failed to save. Please try again.");
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
        try {
            if (casualty.getId().equals(DEFAULT_ID)) {
                addCasualtyToCrash(casualty, crash);
                saveMessage(request, "Casualty added successfully.");
            } else {
                updateCrashCasualty(casualty, crash);
                saveMessage(request, "Casualty updated successfully.");
            }
            crash.setDirty(true);
        } catch (Exception e) {
            logException(request, e, "FAILURE: Casualty information failed to save. Please try again.");
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
        Map<String, List> referenceData = crashService.getReferenceData();
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
            crashService.removeCasualtyFromCrash(crash, Long.parseLong(id));
            request.getSession().setAttribute("crash", crash);
            saveMessage(request, "Casualty removed successfully");
            crash.setDirty(true);
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
            crashService.removeVehicleFromCrash(crash, Long.parseLong(id));
            CrashFormHelper.resetVehicleNumbers(crash);
            request.getSession().setAttribute("crash", crash);
            saveMessage(request, "Vehicle removed successfully");
            crash.setDirty(true);
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
            VehicleType vt = vehicleTypeService.get(vehicle.getVehicleType()
                    .getId());
            vehicle.setVehicleType(vt);
        }
    }

    private void setDriverCasualtyType(Driver driver) {
        if (driver.getCasualtyType() != null) {
            CasualtyType casualtyType = casualtyTypeService.get(driver
                    .getCasualtyType().getId());
            driver.setCasualtyType(casualtyType);
        }
    }

    private void setCasualtyParams(Casualty casualty, Crash crash) {
        if (casualty.getCasualtyClass() != null) {
            CasualtyClass casualtyClass = casualtyClassService.get(casualty
                    .getCasualtyClass().getId());
            casualty.setCasualtyClass(casualtyClass);
        }
        if (casualty.getCasualtyType() != null) {
            CasualtyType casualtyType = casualtyTypeService.get(casualty
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
