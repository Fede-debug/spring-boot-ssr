package com.in28minutes.springboot.controller;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.in28minutes.springboot.model.Question;
import com.in28minutes.springboot.service.SurveyService;

//untit test: we only test a specific class (SurveyController)
//insted of using the original service, we mock it

@RunWith(SpringRunner.class) //helps launching a Spring context
@WebMvcTest(value = SurveyController.class)  //test only this 
// disable autoconfig of spring security because it's a unit test
@AutoConfigureMockMvc(addFilters = false)
public class SurveyControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	//Mock @Autowired
	@MockBean
	private SurveyService surveyService;
	
	@Test
	public void retreiveDetailsForQuestion() throws Exception {
		//use this specific data (mock data)
		//the 'true' service call the retrieveQuestion method
		
		//1.SETUP: When(survey.retrieveQuestion("ANY", "ANY")).ReurnSomeMockData
		Question mockQuestion = new Question("Question1",
				"Largest Country in the World", "Russia", Arrays.asList(
						"India", "Russia", "United States", "China"));
		
		Mockito.when(
				surveyService.retrieveQuestion(Mockito.anyString(), 
						Mockito.anyString())).thenReturn(mockQuestion);
		
		//2.INVOCATION: make a call to the service
		//GET /surveys/Survey1/questions/Question1
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/surveys/Survey1/questions/Question1").accept(
						MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		//result.getResponse().getContentAsString();
		
		String expected = "{\"id\":\"Question1\", \"description\":\"Largest Country in the World\", \"correctAnswer\":\"Russia\"}";
		
		//3.VERIFICATION: Expect this response back -> assert
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	/*
	 *	@Test
    	public void retrieveSurveyQuestions() throws Exception {
        	List<Question> mockList = Arrays.asList(
                new Question("Question1", "First Alphabet", "A", Arrays.asList(
                        "A", "B", "C", "D")),
                new Question("Question2", "Last Alphabet", "Z", Arrays.asList(
                        "A", "X", "Y", "Z")));

        	when(service.retrieveQuestions(anyString())).thenReturn(mockList);

        	MvcResult result = mvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/surveys/Survey1/questions").accept(
                                        MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        	String expected = "["
                + "{id:Question1,description:First Alphabet,correctAnswer:A,options:[A,B,C,D]},"
                + "{id:Question2,description:Last Alphabet,correctAnswer:Z,options:[A,X,Y,Z]}"
                + "]";

        	JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }
	 * */
	 
	@Test
	public void createsurveyQuestion() throws Exception {
		
		//samplequestion (mock) in bean and json format
		Question mockQuestion = new Question("1", "Smallest Number", "1",
				Arrays.asList("1", "2", "3", "4"));

		String questionJson = "{\"description\":\"Smallest Number\",\"correctAnswer\":\"1\",\"options\":"
				+ "[\"1\",\"2\",\"3\",\"4\"]}";
		
		//send question in the body of POST request
		// /survey/Survey1/questions
		
		//I'll mock surveyService.addQuestion() to
		//respond back with mockQuestion
		Mockito.when(
				surveyService.addQuestion(Mockito.anyString(), Mockito
						.any(Question.class))).thenReturn(mockQuestion);
		
		//Send question as body to /surveys/Survey1/questions
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/surveys/Survey1/questions")
				.accept(MediaType.APPLICATION_JSON).content(questionJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		//Asserts
		//check STATUS and HEADERS
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertEquals("http://localhost/surveys/Survey1/questions/1", response
				.getHeader(HttpHeaders.LOCATION));
		
		
		
	}
}


