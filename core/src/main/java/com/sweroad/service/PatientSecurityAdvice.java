package com.sweroad.service;

import com.sweroad.Constants;
import com.sweroad.model.Patient;
import com.sweroad.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class PatientSecurityAdvice {

    @Autowired
    private UserService userService;
    private boolean editable;
    private boolean removable;
    private boolean editableOnlyForHospital;
    private User currentUser;

    @AfterReturning(pointcut = "execution(* *..service.PatientManager.getPatients(..))",
            returning = "returnValue")
    public void afterReturningPatients(JoinPoint jp, Object returnValue) throws Throwable {
        setUserAccess();
        for (Patient patient : ((List<Patient>) returnValue)) {
            setPatientEditable(patient);
            patient.setRemovable(removable);
        }
    }

    @AfterReturning(pointcut = "execution(* *..service.PatientManager.getPatientForView(..))",
            returning = "returnValue")
    public void afterReturningOnePatient(JoinPoint jp, Object returnValue) {
        setUserAccess();
        setPatientEditable((Patient) returnValue);
        ((Patient)returnValue).setRemovable(removable);
    }

    private void setUserAccess() {
        currentUser = userService.getCurrentUser();
        if (currentUser.hasRole(Constants.SUPER_USER_ROLE)
                || currentUser.hasRole(Constants.ADMIN_ROLE)) {
            editableOnlyForHospital = false;
            editable = true;
            removable = true;
        } else if (currentUser.hasRole(Constants.USER_ROLE)) {
            editableOnlyForHospital = true;
            editable = true;
            removable = false;
        } else {
            editableOnlyForHospital = false;
            editable = false;
            removable = false;
        }
    }

    private void setPatientEditable(Patient patient) {
        if (editable && editableOnlyForHospital && patient.getHospital() != null) {
            if (patient.getHospital().equals(currentUser.getHospital())) {
                patient.setEditable(true);
            } else {
                patient.setEditable(false);
            }
        } else {
            patient.setEditable(editable);
        }
    }
}
