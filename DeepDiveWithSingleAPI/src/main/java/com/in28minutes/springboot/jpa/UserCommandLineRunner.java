package com.in28minutes.springboot.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCommandLineRunner implements CommandLineRunner{

	
	private static final Logger log = LoggerFactory
            .getLogger(UserCommandLineRunner.class);
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("UserCommandLineRunner");
		 repository.save(new User("Ranga", "Admin"));
         repository.save(new User("Ravi", "User"));
         repository.save(new User("Satish", "Admin"));
         repository.save(new User("Raghu", "User"));
         
         for(User user : repository.findAll() ) {
        	 log.info(user.toString());
         }
         
         log.info("-------------------------------");
         log.info("Finding user with id 1");
         log.info("-------------------------------");
         User user = repository.findById(1L).get();
         log.info(user.toString());

         log.info("-------------------------------");
         
         log.info("Finding all Admins");
         log.info("-------------------------------");
         for (User admin : repository.findByRole("Admin")) {
             log.info(admin.toString());
         }

	//we can do database 'queries' in here using the UserRepository
	//commandLineRunners are invoked as soon as the spring boot application is run
	//and can be used to populate our db at runtime
	}     
}
