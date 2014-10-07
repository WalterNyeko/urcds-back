package com.sweroad.service;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.sweroad.Constants;
import com.sweroad.model.Crash;
import com.sweroad.model.User;

public class CrashSecurityAdvice implements AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		SecurityContext sc = SecurityContextHolder.getContext();
		Authentication auth = sc.getAuthentication();
		boolean editable = false;
		boolean removable = false;
		if (auth != null) {
			User currentUser = getCurrentUser(auth);
			// This if suppresses IDE warning and should be removed when TODO below is implemented.
			if (currentUser.getId() != null)
				;
			Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
			if (authsContainRole(Constants.USER_ROLE, authorities)) {
				// TODO Implement logic to check user's district/region and set editability accordingly
				editable = true;
				removable = false;
			}
			if (authsContainRole(Constants.SUPER_USER_ROLE, authorities)
					|| authsContainRole(Constants.ADMIN_ROLE, authorities)) {
				editable = true;
				removable = true;
			}
		}
		setCrashModifiability(returnValue, auth, editable, removable);
	}

	@SuppressWarnings("unchecked")
	private void setCrashModifiability(Object returnValue, Authentication auth, boolean editable, boolean removable) {
		List<Crash> crashes = (List<Crash>) returnValue;
		for (Crash crash : crashes) {
			crash.setEditable(editable);
			crash.setRemovable(removable);
		}
	}

	private boolean authsContainRole(String role, Collection<? extends GrantedAuthority> authorities) {
		for (GrantedAuthority authority : authorities) {
			if (authority.getAuthority().equals(role)) {
				return true;
			}
		}
		return false;
	}

	private User getCurrentUser(Authentication auth) {
		User currentUser;
		if (auth.getPrincipal() instanceof UserDetails) {
			currentUser = (User) auth.getPrincipal();
		} else if (auth.getDetails() instanceof UserDetails) {
			currentUser = (User) auth.getDetails();
		} else {
			throw new AccessDeniedException("User not properly authenticated.");
		}
		return currentUser;
	}
}
