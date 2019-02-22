package br.eti.esabreu.ewertonconsultoria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class DashboardController {
	
	@GetMapping
	public ModelAndView dashboard() {
		ModelAndView mView = new ModelAndView("consultoria/dashboard");
		return mView;
	}
	
	@GetMapping("/error")
	public ModelAndView notFound(ModelAndView view) {
		view.setViewName("consultoria/nao-encontrado");
		return view;
	}
}