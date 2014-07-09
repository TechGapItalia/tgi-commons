package it.tgi.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * Cattura l'evento di Login failure, evitando il redirect (302) e sostituendo tale codice con il (400)
 */
@Component("restFailureHandler")
public class RestFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private static final Logger log = LoggerFactory.getLogger(RestFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String message = "Authentication Failed: " + exception.getMessage();
		log.warn(message);
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
	}

}