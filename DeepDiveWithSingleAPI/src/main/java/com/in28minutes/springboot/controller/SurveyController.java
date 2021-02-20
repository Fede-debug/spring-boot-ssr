package com.in28minutes.springboot.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.springboot.model.Question;
import com.in28minutes.springboot.service.SurveyService;

/*
 * "/surveys/{surveyId/questions}"
 * 
 * SurveyController
 * SurveyService - Autowire
 * PathVariable - surveyId
 * "/surveys/survey1/questions"
 * 
 * SurveyService
 * com.in28minutes.springboot.service.SurveyService.retrieveQuestions(String)
 */

@RestController
public class SurveyController {

	//dependency injection
	@Autowired //add this after the surveyService declaration
	private SurveyService surveyService;
	
	//method to expose url
	//"/surveys/{surveyId}/questions" GET
	@GetMapping("/surveys/{surveyId}/questions")
	public List<Question> retrieveQuestionsForSurvey(@PathVariable String surveyId){
		return surveyService.retrieveQuestions(surveyId);
	}
	
	/*
	* format of question:
	* {
		  "id": "Question3",
		  "description": "Highest GDP in the World",
		  "correctAnswer": "United States",
		  "options": [
		    "India",
		    "Russia",
		    "United States",
		    "China"
		  ]
	}
	 * */
	//POST a new question
	@PostMapping("/surveys/{surveyId}/questions")
	public ResponseEntity<Void> addQuestionToSurvey(@PathVariable String surveyId, @RequestBody Question newQuestion){
		//@RequestBody to map request to object
		
		//details of the question in the request body, structure?
		//how to we map such structure to the Question object?
		//if mapping is successful, what should be returned?
		//what is the response status?
		
		Question question = surveyService.addQuestion(surveyId, newQuestion);
		//return error 204 if URI is malformed or could not add question
		//for some other reason
		if(question == null)
			return ResponseEntity.noContent().build();
		
		
		//Success - URI of the new resource in Response header (RESTful service standard)
		//Status - created
		//URI -> /surveys/{surveyId}/questions/{questionId} question.getQuestionId()
		//ServletUriComponentsBuilder utility class from spring framework
		//appends the id from current request replancing it with question.getId()
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
				"/{id}").buildAndExpand(question.getId()).toUri();
		
		//Status
		//ReponseEntity is utility class that helps create
		//stuff with specific statuses
		//returns a code 201 created
		return ResponseEntity.created(location).build();
		
		//I receive a status 406 not acceptable is client ask a XML
		//unless I specifically add a dependency for XML conversion
		//and Spring boot autoconfigures it! jackson-dataformat-xml
		// Accept - application/xml in the header
		//with Content-type application/xml I can POST XML
	}
	
	//"/surveys/{surveyId}/questions/{questionId}" GET
	@GetMapping("/surveys/{surveyId}/questions/{questionId}")
	public Question retrieveDetailsForQuestion(@PathVariable String surveyId, @PathVariable String questionId){
		return surveyService.retrieveQuestion(surveyId, questionId);
	}
	
	
}
