package com.in28minutes.springboot.web.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in28minutes.springboot.web.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer>{
	
	//need to add methods not present in the service
	List<Todo> findByUser(String name);
	// (method name is findByColumn)
}

/*
 *  TodoService has the following methods 
 *  that are also presents in the repository
 *  (from the TodoController)
 * 
 *  service.deleteTodo(id)
 *  service.retrieveTodo(id)
 *  service.updateTodo
 *  service.addTodo(getLoggedUserName(model), todo.getDesc(), todo.getTargetDate(), false)
 *  
 *  this one is NOT present
 *  
 *  service.retrieveTodos(name)
 * */
 