package com.in28minutes.springboot.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{
	//findBy{ColumnName} spring data jpa auto-implement
	//the method by itself!
	
	List<User> findByRole(String role);
	
}
