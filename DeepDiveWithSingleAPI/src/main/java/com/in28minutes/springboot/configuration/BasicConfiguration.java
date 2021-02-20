package com.in28minutes.springboot.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


//this kind of configuration is type safe
//and gets added to configprops as available config in actuator
@Component  //gets scanned as standard bean 
@ConfigurationProperties("basic")  //creates a config bean
public class BasicConfiguration {
	
	private boolean value;
	private String message;
	private int number;
	
	public boolean isValue() {
		return value;
	}
	public void setValue(boolean value) {
		this.value = value;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

}
