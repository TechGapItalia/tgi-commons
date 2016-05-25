package it.techgap.common.security.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Cattura l'evento di Login failure, evitando il redirect (302) e sostituendo tale codice con il (400)
 */
public class RestJSONFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private static final Logger log = LoggerFactory.getLogger(RestJSONFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String message = "Authentication Failed: " + exception.getMessage();
		log.warn(message);
		String jsonResponse = "{\"message\":\""+exception.getMessage()+"\"}";
		response.getWriter().write(jsonResponse);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().flush();
	}

}