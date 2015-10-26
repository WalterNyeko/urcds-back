package com.sweroad.webapp.controller;

import com.sweroad.model.Crash;
import com.sweroad.model.Patient;
import com.sweroad.model.PoliceStation;
import com.sweroad.service.PatientManager;
import com.sweroad.webapp.util.SessionHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Frank on 3/18/15.
 */
@Controller
@RequestMapping("/patient*")
public class PatientController extends BaseFormController {

    @Autowired
    private PatientManager patientManager;
    private final static Long DEFAULT_ID = 0L;

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public ModelAndView showPatients() throws Exception {
        ModelAndView mav = new ModelAndView("patient/patients");
        return mav;
    }

    @RequestMapping(value = "/patientform", method = RequestMethod.GET)
    public ModelAndView showForm(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("patient/patientform");
        Patient patient;
        String id = request.getParameter("id");
        if (!StringUtils.isBlank(id)) {
            patient = patientManager.get(Long.parseLong(id));
        } else {
            patient = new Patient();
            patient.setId(DEFAULT_ID);
        }
        mav.addObject("patient", patient);
        mav.addAllObjects(patientManager.getReferenceData());
        return mav;
    }
}