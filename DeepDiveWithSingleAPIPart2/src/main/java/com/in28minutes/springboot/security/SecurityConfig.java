package com.in28minutes.springboot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration  //bean for configuration
//WebSecurityConfigurerAdapter helps us configure security
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	//Authentication: Users and roles? User -> Role mapping
	//hardcoded user with in memory db
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		  	auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
		  	.withUser("user1").password("secret1").roles("USER").and()
		  	.withUser("admin1").password("secret1").roles("USER", "ADMIN");
		  	//admin has both roles!
	}
	
	
	//Authorization: What roles/users have access to? Role -> Access mapping
	//anything wuth survey in it -> user access
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		

		http.httpBasic().and().authorizeRequests()
		.antMatchers("/surveys/**").hasRole("USER") //allow urls only for users with role USER
		.antMatchers("/users/**").hasRole("USER")
		.antMatchers("/**").hasRole("ADMIN")  //any other url requires admin
		.and().csrf().disable() // for frames in actuator
		.headers().frameOptions().disable(); //makes sure our HAL browser is working
			
	}
}
