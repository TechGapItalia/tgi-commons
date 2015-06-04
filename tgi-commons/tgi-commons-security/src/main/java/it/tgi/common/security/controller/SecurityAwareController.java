package it.tgi.common.security.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class SecurityAwareController<U extends Authentication> {

	@SuppressWarnings("unchecked")
	protected U getAuthenticationUser() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken)
			return null;
		else
			return (U) authentication;
	}

	protected String getCurrentUsername() {
		final U authenticationUser = getAuthenticationUser();
		return authenticationUser == null ? null : authenticationUser.getName();
	}

}