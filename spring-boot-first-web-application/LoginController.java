/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.in28minutes.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;

import com.in28minutes.springboot.web.service.LoginService;

/**
 *
 * @author Federico
 */

@Controller
public class LoginController {
	
	//LoginService service = new LoginService(); classic java
	@Autowired
	LoginService service;
    
    @RequestMapping(value="/login", method = RequestMethod.GET) //only for GET (or use GetMapping)
    
    
    //@ResponseBody
    //@RequestParam String name, 
    public String showLoginPage(ModelMap model){
    	//System.out.println("name is " + name); prints in console
    	/*put the value in the model
    	 * and makes name available to the view (jsp)*/
    	//model.put("name", name);  
		return "login";
    }
    
    @RequestMapping(value="/login", method = RequestMethod.POST) //only for GET (or use GEtMapping)
    
    public String showWelcomePage(ModelMap model, @RequestParam String name, @RequestParam String password){
    	
    	boolean isValidUser = service.validateUser(name, password);
    	/*put the value in the model
    	 * and makes name available to the view (jsp)*/
    	if(!isValidUser) {
    		model.put("errorMessage", "Invalid Credentials");
    		return "login";
    	}
    	model.put("name", name);
    	model.put("password", password);
		return "welcome";
    	
    }
    
}
