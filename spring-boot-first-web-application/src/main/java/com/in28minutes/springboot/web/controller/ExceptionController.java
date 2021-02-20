package com.in28minutes.springboot.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Controller("error")
public class ExceptionController {
	
	//private Logger log = LoggerFactory.getLogger(ErrorController.class);
	private static final Logger log = 
			LoggerFactory.getLogger(ExceptionController.class);
	
	@ExceptionHandler(Exception.class)
	// controllers methods can return a ModelAndView
	// alternate way instead of ModelView map
	public ModelAndView handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		
		log.error("Request: " + request.getRequestURL() + " raised " + ex);
		
		ModelAndView mv = new ModelAndView();
		//add details to the model and redirect user to custom error page
		mv.addObject("exception", ex.getStackTrace());
		mv.addObject("exception message", ex.getLocalizedMessage());
		mv.addObject("url", request.getRequestURL());
		mv.setViewName("error");
		
		return mv;
	}
}
