package com.in28minutes.springboot.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	//Create Users - in28Minutes/dummy
	@Autowired  //creates bean for auth manager
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()).withUser("in28Minutes").password("dummy")
                .roles("USER", "ADMIN");
        //protects all pages for unauthorized access
    }
	
	//tell spring security we use a form instead of the window
	// specifies the pages we can access
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login", "/h2-console/**").permitAll()
                .antMatchers("/", "/*todo*/**").access("hasRole('USER')").and()
                .formLogin();
        
        http.csrf().disable();
        http.headers().frameOptions().disable();
        
        //disable security measure for h2-console
        //that is auto enabled by spring security
        // because h2-console runs in a frame
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
	
}
