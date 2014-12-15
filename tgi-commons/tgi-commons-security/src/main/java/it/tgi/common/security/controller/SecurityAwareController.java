package it.tgi.common.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class SecurityAwareController<U extends Authentication> {

    protected U getAuthenticationUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (U) authentication;
    }

    protected String getCurrentUsername() {
        final U authenticationUser = getAuthenticationUser();
        return authenticationUser == null ? null : authenticationUser.getName();
    }

}