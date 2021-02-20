package com.in28minutes.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//Spring to manage this bean and create and instance of this
@Component //or service
public class WelcomeService{
	
	@Value("${welcome.message}") //autowires values from property file in beans or program argument
	private String welcomeMessage;
	//command line arguments have higher priority than properties file ones
	//or you could have different properties files and load them in cmd with 
	// --spring.config.location=classpath:/default.properties
	// check actuator/env for details
	
	public String retrieveWelcomeMessage() {
		//return "Welcome to morning!!!";
		return welcomeMessage;
	}
}