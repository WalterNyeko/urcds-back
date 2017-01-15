package com.sweroad.webapp.controller;

import com.sweroad.model.Patient;
import com.sweroad.service.PatientService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Frank on 3/18/15.
 */
@Controller
@RequestMapping("/patient*")
public class PatientController extends BaseFormController {

    @Autowired
    private PatientService patientService;
    private final static Long DEFAULT_ID = 0L;

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public ModelAndView showPatients() throws Exception {
        ModelAndView mav = new ModelAndView("patient/patients");
        mav.addObject("patients", patientService.getPatients());
        return mav;
    }

    @RequestMapping(value = "/patientform", method = RequestMethod.GET)
    public ModelAndView showForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("patient/patientform");
        Patient patient = new Patient();
        String id = request.getParameter("id");
        if (!StringUtils.isBlank(id)) {
            try {
                patient = patientService.get(Long.parseLong(id));
                request.getSession().setAttribute("patientJSON", patient.toJSON());
            }  catch (Exception e) {
                logException(request, e, "Failed to load patient form in edit mode. Please contact your System Administrator.");
                response.sendRedirect(request.getContextPath() + "/patients");
            }
        } else {
            patient.setId(DEFAULT_ID);
        }
        mav.addObject("patient", patient);
        mav.addAllObjects(patientService.getReferenceData());
        return mav;
    }
    
    @RequestMapping(value = "/patientform", method = RequestMethod.POST)
    public void savePatient(Patient patient, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            patientService.savePatient(patient);
            saveMessage(request, "Patient was saved successfully.");
        } catch (Exception e) {
            logException(request, e, "Patient data failed to save. Please contact your System Administrator.");
        }
        response.sendRedirect(request.getContextPath() + "/patients");
    }

    @RequestMapping(value = "/patientview", method = RequestMethod.GET)
    public ModelAndView viewPatient(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("patient/patientview");
        try {
            Patient patient;
            String id = request.getParameter("id");
            patient = patientService.getPatientForView(new Long(id));
            mav.addObject("patient", patient);
            mav.addAllObjects(patientService.getReferenceData());
        } catch (Exception e) {
            log.error("View patient failed: " + e.getLocalizedMessage());
            response.sendRedirect(request.getContextPath() + "/patients");
        }
        return mav;
    }
}