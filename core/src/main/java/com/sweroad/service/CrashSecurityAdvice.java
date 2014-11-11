package com.sweroad.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sweroad.Constants;
import com.sweroad.model.Crash;
import com.sweroad.model.District;
import com.sweroad.model.User;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CrashSecurityAdvice {

    private boolean editable;
    private boolean removable;
    private boolean editableOnlyForDistrict;
    private boolean isAdmin;
    private User currentUser;

    @AfterReturning(pointcut = "execution(* *..service.CrashManager.getCrashes(..))",
            returning = "returnValue")
    public void afterReturningCrashes(JoinPoint jp, Object returnValue) throws Throwable {
        determineUserRoles();
        setCrashChangeAbility((List<Crash>) returnValue);
        if (!isAdmin) {
            removeInvisibleCrashes(returnValue);
        }

    }

    @AfterReturning(pointcut = "execution(* *..service.CrashManager.getCrashForView(..))",
            returning = "returnValue")
    public void afterReturningOneCrash(JoinPoint jp, Object returnValue) {
        determineUserRoles();
        setCrashEditability((Crash)returnValue);
        ((Crash)returnValue).setRemovable(removable);
    }

    private void determineUserRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            currentUser = UserSecurityAdvice.getCurrentUser(auth, null);
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            if (authoritiesContainRole(Constants.USER_ROLE, authorities)) {
                editableOnlyForDistrict = true;
                editable = true;
                removable = false;
            }
            if (authoritiesContainRole(Constants.SUPER_USER_ROLE, authorities)
                    | (isAdmin = authoritiesContainRole(Constants.ADMIN_ROLE, authorities))) {
                editable = true;
                removable = true;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void setCrashChangeAbility(List<Crash> crashes) {
        for (Crash crash : crashes) {
            setCrashEditability(crash);
            crash.setRemovable(removable);
        }
    }

    private void setCrashEditability(Crash crash) {
        if (editable && editableOnlyForDistrict) {
            crash.setEditable(crash.isEditableForDistrict(currentUser.getDistrict().getId()));
        } else {
            crash.setEditable(editable);
        }
    }

    @SuppressWarnings("unchecked")
    private void removeInvisibleCrashes(Object returnValue) {
        List<Crash> crashes = new ArrayList<Crash>();
        for (Crash crash : (List<Crash>) returnValue) {
            if (crash.isRemoved()) {
                crashes.add(crash);
            }
        }
        ((List<Crash>) returnValue).removeAll(crashes);
    }

    private boolean authoritiesContainRole(String role, Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(role)) {
                return true;
            }
        }
        return false;
    }
}
