package com.in28minutes.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

@SpringBootApplication //also triggers component scan
// in this specific package, otherwise:
//@ComponentScan("com.in28minutes")
public class SpringBootFirstWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFirstWebApplication.class, args);
		//for(int i=0; i<10 ; i++)
			//System.out.println("lollllllllllllllllll");
		
		
	}
	
	@Profile("prod")
	@Bean
	public String dummy() {
		return "something";
	}
}