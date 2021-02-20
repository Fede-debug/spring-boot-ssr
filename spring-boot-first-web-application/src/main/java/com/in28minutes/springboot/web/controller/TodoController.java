/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.in28minutes.springboot.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.in28minutes.springboot.web.model.Todo;
import com.in28minutes.springboot.web.service.TodoRepository;
//import com.in28minutes.springboot.web.service.LoginService;
import com.in28minutes.springboot.web.service.TodoService;

/**
 *
 * @author Federico
 */

@Controller
//@SessionAttributes("name") don't need it with spring security
public class TodoController {
	
	//LoginService service = new LoginService(); classic java
	@Autowired
	TodoService service;
	
	//from the jpa repository
	@Autowired
	TodoRepository repository;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		//Date - dd/MM/yyyy
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}
    
    @RequestMapping(value="/list-todos", method = RequestMethod.GET) //only for GET (or use GetMapping)
    
    
    //@ResponseBody
    //@RequestParam String name, 
    public String showTodos(ModelMap model){
    	//System.out.println("name is " + name); prints in console
    	/*put the value in the model
    	 * and makes name available to the view (jsp)*/
    	//model.put("name", name);  
    	String name = getLoggedUserName(model);
    	//System.out.println(name);
    	model.put("todos", repository.findByUser(name));
    	//model.put("todos", service.retrieveTodos(name) );
		return "list-todos";
    }

	private String getLoggedUserName(ModelMap model) {
		//from session:
		//return (String) model.get("name");
		//from spring security:
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		return principal.toString();
	}
 
// il value della richiesta arriva da un link, non dipende da
    //come chiamiamo le pagine .jsp
/*
	@RequestMapping(value="/add-todo", method = RequestMethod.GET) //only for GET (or use GetMapping)
	public String showAddTodoPage(ModelMap model){
	return "todo";
	    }
*/ 

	@RequestMapping(value="/add-todo", method = RequestMethod.GET) //only for GET (or use GetMapping)
	public String showAddTodoPage(ModelMap model){
		model.addAttribute("todo", new Todo(0, getLoggedUserName(model), "Default Desc", new Date(), false));
		return "todo";
	}  

/*
	@RequestMapping(value="/add-todo", method = RequestMethod.POST) 
	
	public String addTodo(ModelMap model, @RequestParam String desc){
		service.addTodo((String) model.get("name"), desc, new Date(), false);
		// uso redirect per non duplicare la logica
		// di showTodos
		return "redirect:/list-todos";
	}
*/

	@RequestMapping(value="/add-todo", method = RequestMethod.POST) 
	public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result){
		if(result.hasErrors()) {
			return "todo";
		}
		
		//for the DB (h2 or MySql)
		todo.setUser(getLoggedUserName(model));
		

		repository.save(todo);
		
		
		//service.addTodo(getLoggedUserName(model), todo.getDesc(), todo.getTargetDate(), false);
		// uso redirect per non duplicare la logica
		// di showTodos
		return "redirect:/list-todos";
	}	
    
	@RequestMapping(value="/delete-todo", method = RequestMethod.GET) //only for GET (or use GetMapping)
	public String deleteTodo(@RequestParam int id){
		
		//if(id == 1)
			//throw new RuntimeException("Something went wrong");
		
		repository.deleteById(id);
		//service.deleteTodo(id);
		return "redirect:/list-todos";
	}

	@RequestMapping(value="/update-todo", method = RequestMethod.GET) //only for GET (or use GetMapping)
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model){
		Todo todo = repository.findById(id).get();
		//Todo todo = service.retrieveTodo(id);
		model.put("todo", todo);
		return "todo";
	} 
	
	@RequestMapping(value="/update-todo", method = RequestMethod.POST) 
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result){
		
		if(result.hasErrors()) {
			return "todo";
		}
		
		// needs ModelMap model becuase name is in session
		todo.setUser(getLoggedUserName(model));
		
		repository.save(todo);
		//service.updateTodo(todo);
		
		return "redirect:/list-todos";
	} 
}
