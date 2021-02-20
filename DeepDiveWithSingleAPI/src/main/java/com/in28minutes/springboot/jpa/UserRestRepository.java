package com.in28minutes.springboot.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.query.Param;

//spring data rest exposes services for GET and other requests
//localhost/users/1
//localhost/users7?size=4
//localhost/users/?sort=name,desc
//HATEOAS links

//PagingAndSortingRepository provides more features than CrudRepository
//paging = splits results in pages


//generates lots of URI resources automatically, better not use it in production!
@RepositoryRestResource(path="users", collectionResourceRel="users") //handles URI
public interface UserRestRepository extends PagingAndSortingRepository<User, Long>{
	//findBy{ColumnName} spring data jpa auto-implement
	//the method by itself!
	
	List<User> findByRole(@Param("role") String role);
	//@Param("role") exposes method as rest service
	//http://localhost:8080/users/search/findByRole?role=Admin
	
}
