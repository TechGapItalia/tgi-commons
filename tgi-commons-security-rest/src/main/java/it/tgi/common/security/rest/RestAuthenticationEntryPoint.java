package it.tgi.common.security.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Cattura l'evento di accesso non autorizzato, evitando il redirect (302) e sostituendo tale codice con il (401)
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Logger log = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		log.warn("Blocked unauthorized access attempt to {}", request.getRequestURI());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}

}