/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.in28minutes.springboot.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.in28minutes.springboot.web.service.LoginServiceNotUsed;

/**
 *
 * @author Federico
 */


@Controller
public class LogoutController {
	
	
	
	
	@RequestMapping(value="/logout", method = RequestMethod.GET) //only for GET (or use GetMapping)
     public String logOut(HttpServletRequest request, HttpServletResponse response){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		return "redirect:/";
    }
    
	/*
    @RequestMapping(value="/login", method = RequestMethod.POST) //only for GET (or use GEtMapping)
    public String showWelcomePage(ModelMap model, @RequestParam String name, @RequestParam String password){
    	boolean isValidUser = service.validateUser(name, password);
    	//put the value in the model
    	// and makes name available to the view (jsp)
    	if(!isValidUser) {
    		model.put("errorMessage", "Invalid Credentials");
    		return "login";
    	}
    	model.put("name", name);
    	model.put("password", password);
		return "welcome";
    	
    }
    */
    
}
