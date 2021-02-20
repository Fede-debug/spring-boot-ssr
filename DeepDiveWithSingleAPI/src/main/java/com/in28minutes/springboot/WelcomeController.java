package com.in28minutes.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.springboot.configuration.BasicConfiguration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RestController
//combination of @Controller and @ResponseBody annotation
public class WelcomeController {
	
	//Dependency
	//injected here
	@Autowired // the service class need to have the annotation!
	private WelcomeService welcomeService;// = new WelcomeService();
	
	@Autowired
	private BasicConfiguration configuration;
	
	@GetMapping("/welcome")
	//@ResponseBody not needed
	public String welcome() {
		return welcomeService.retrieveWelcomeMessage();
		
	}
	
	//test service
	@GetMapping("/dynamic-configuration")
	public Map dynamicConfiguration() {
		Map map = new HashMap();
		map.put("message", configuration.getMessage());
		map.put("number", configuration.getNumber());
		map.put("value", configuration.isValue());
		//I'd need three @Value for these settings ifI didn't use
		//a config bean
		
		
		return map;
		
	}

}




