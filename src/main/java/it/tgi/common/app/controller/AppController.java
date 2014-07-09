package it.tgi.common.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller Spring MVC ogni richiesta a "/app/" viene catturata da questo Controller<br />
 * il suo scopo e' quello di redirigere l'output alla pagina iniziale gestita ad angular (vedi "/WEB-INF/app/login.jsp")
 */
@Controller
public class AppController {

	@RequestMapping("/")
	public ModelAndView index() throws Exception {
		return new ModelAndView("index");
	}

}