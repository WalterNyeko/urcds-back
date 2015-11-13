package com.sweroad.service;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.sweroad.Constants;
import com.sweroad.model.Crash;
import com.sweroad.model.User;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CrashSecurityAdvice {

    @Autowired
    private UserManager userManager;
    private boolean editable;
    private boolean removable;
    private boolean editableOnlyForDistrict;
    private User currentUser;

    @AfterReturning(pointcut = "execution(* *..service.CrashManager.getCrashes(..))",
            returning = "returnValue")
    public void afterReturningCrashes(JoinPoint jp, Object returnValue) throws Throwable {
        setUserAccess();
        for (Crash crash : ((List<Crash>) returnValue)) {
            setCrashEditable(crash);
            crash.setRemovable(removable);
        }
    }

    @AfterReturning(pointcut = "execution(* *..service.CrashManager.getCrashForView(..))",
            returning = "returnValue")
    public void afterReturningOneCrash(JoinPoint jp, Object returnValue) {
        setUserAccess();
        setCrashEditable((Crash) returnValue);
        ((Crash)returnValue).setRemovable(removable);
    }

    private void setUserAccess() {
        currentUser = userManager.getCurrentUser();
        if (currentUser.hasRole(Constants.SUPER_USER_ROLE)
                || currentUser.hasRole(Constants.ADMIN_ROLE)) {
            editableOnlyForDistrict = false;
            editable = true;
            removable = true;
        } else if (currentUser.hasRole(Constants.USER_ROLE)) {
            editableOnlyForDistrict = true;
            editable = true;
            removable = false;
        } else {
            editableOnlyForDistrict = false;
            editable = false;
            removable = false;
        }
    }

    private void setCrashEditable(Crash crash) {
        if (editable && editableOnlyForDistrict && crash.getPoliceStation() != null && crash.getPoliceStation().getDistrict() != null) {
            if (crash.getPoliceStation().getDistrict().equals(currentUser.getDistrict())) {
                crash.setEditable(true);
            } else {
                crash.setEditable(false);
            }
        } else {
            crash.setEditable(editable);
        }
    }
}
