package com.sweroad.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sweroad.Constants;
import com.sweroad.model.Crash;
import com.sweroad.model.District;
import com.sweroad.model.User;

public class CrashSecurityAdvice implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication auth = sc.getAuthentication();
        boolean editable = false;
        boolean removable = false;
        boolean editableOnlyForDistrict = false;
        boolean isAdmin = false;
        if (auth != null) {
            User currentUser = UserSecurityAdvice.getCurrentUser(auth, null);
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
            setCrashChangeAbility((List<Crash>) returnValue, editable, removable, editableOnlyForDistrict, currentUser.getDistrict());
            if (!isAdmin) {
                removeInvisibleCrashes(returnValue);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void setCrashChangeAbility(List<Crash> crashes, boolean editable, boolean removable,
                                       boolean editableOnlyForDistrict, District district) {
        if (!editableOnlyForDistrict) {
            for (Crash crash : crashes) {
                crash.setEditable(editable);
                crash.setRemovable(removable);
            }
        } else {
            for (Crash crash : crashes) {
                if (editable && (district != null && district.equals(crash.getPoliceStation().getDistrict()))) {
                    crash.setEditable(true);
                } else {
                    crash.setEditable(false);
                }
                crash.setRemovable(removable);
            }
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
