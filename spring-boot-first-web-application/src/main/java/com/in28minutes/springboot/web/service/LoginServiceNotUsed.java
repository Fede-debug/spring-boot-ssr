package com.in28minutes.springboot.web.service;

import org.springframework.stereotype.Component;

@Component //Spring boots creates an instance for LoginController (to inject dependency)
public class LoginServiceNotUsed {
	
	public boolean validateUser(String userid, String password) {
		//in28minutes, dummy
		//System.out.println(userid.equalsIgnoreCase("in28minutes") && password.equalsIgnoreCase("dummy"));
		//return userid.equalsIgnoreCase("in28minutes") && password.equalsIgnoreCase("dummy");
		return userid.equals("in28Minutes") && password.equals("dummy");
	}

}
