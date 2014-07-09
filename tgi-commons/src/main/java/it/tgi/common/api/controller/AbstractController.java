package it.tgi.common.api.controller;

import it.tgi.common.security.model.AbstractAuthenticationUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractController {

	private static final Logger log = LoggerFactory.getLogger(AbstractController.class);

	protected AbstractAuthenticationUser getAuthenticationUser() {
		log.trace("getAuthenticationUser()");

		return (AbstractAuthenticationUser) SecurityContextHolder.getContext().getAuthentication();
	}

	protected String getCurrentUsername() {
		log.trace("getCurrentUsername()");

		AbstractAuthenticationUser authenticationUser = getAuthenticationUser();
		return authenticationUser == null ? null : authenticationUser.getName();
	}

}