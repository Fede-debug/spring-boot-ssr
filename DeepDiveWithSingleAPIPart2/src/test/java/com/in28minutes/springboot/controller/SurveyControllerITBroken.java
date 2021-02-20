package com.in28minutes.springboot.controller;

import static org.junit.Assert.*;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.in28minutes.springboot.Application;
import com.in28minutes.springboot.model.Question;


//integration test -> launches the entire application
@RunWith(SpringRunner.class) //helps launching a Spring context
@SpringBootTest(classes= Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// lauches a spring boot application in a test
public class SurveyControllerITBroken {
	
	//need to find the port for the URL!
	@LocalServerPort
	private int port;
	
	TestRestTemplate restTemplate = new TestRestTemplate();
	
	//I want Json -> Accept: application/json in header
	//I use HttpEntity because TestRestTeplate doesn't support headers too well
	//HttpHeaders headers = new HttpHeaders();
	
	//I added security so I need to handle basic authentication
	HttpHeaders headers = createHttpHeaders("user1", "secret1");
	
	private HttpHeaders createHttpHeaders(String userId, String password) {
		//Basic auth
		HttpHeaders headers = new HttpHeaders();
		//userId, password, Basic
		//"Authorization", "Basic" + Base64Encoding (userId + ":" + password)
		//I don't want to send the user and password in plain text!
		
		String auth = userId + ":" + password;
		byte[] encodedAuth = Base64.encode(auth.getBytes(Charset
                 .forName("US-ASCII")));
		
		String headerValue = "Basic" + new String(encodedAuth);
		headers.add("Authorization", headerValue);
		return headers;
	}

	
	
	@Before
	public void before() {
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		//setAccept requires a List<T>
				
		
	}
	
	
	

	@Test
	public void testJsonAssert() throws JSONException {
		//JSONAssert.assertEquals(expected, actual, strict);
		JSONAssert.assertEquals("{id:1}", "{id:1, name:Ranga}", false);
		//succeed because with strict=false it olny checks specified parameters
		//otherwise with strict = true every field must be present
	}
	
	
	@Test
	public void testRetrieveSurveyQuestion() throws JSONException {
		//fail("Not yet implemented");
		//System.out.println("PORTPORT" + port);
		
		HttpEntity entity = new HttpEntity<String>(null, headers);  //(body, headers)
		
		String url = createURLWithPort("/surveys/Survey1/questions/Question1");
		//invoke our url
		
		//test get request -> getForObject converts or tries to convert response to which class we want to
		//String output = restTemplate.getForObject(url, String.class);
		
		
		
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		
		
		System.out.println("Response: " + response.getBody());
		//Response: {"id":"Question1","description":"Largest Country in the World","correctAnswer":"Russia","options":["India","Russia","United States","China"]}
		assertTrue(response.getBody().contains("\"id\":\"Question1\""));
		assertTrue(response.getBody().contains("\"description\":\"Largest Country in the World\""));
		
		String expected = "{\"id\":\"Question1\", \"description\":\"Largest Country in the World\", \"correctAnswer\":\"Russia\"}";
		JSONAssert.assertEquals(expected, response.getBody(), false);
		
	}
	
	/*
	@Test
	public void retrieveSurveyQuestions() throws Exception {
		
		
        ResponseEntity<List<Question>> response = template.exchange(
	        createUrl("/surveys/Survey1/questions/"), HttpMethod.GET,
	        new HttpEntity<String>("DUMMY_DOESNT_MATTER", headers),
	        new ParameterizedTypeReference<List<Question>>() {
	        });

        Question sampleQuestion = new Question("Question1",
	                "Largest Country in the World", "Russia", Arrays.asList(
	                        "India", "Russia", "United States", "China"));

        assertTrue(response.getBody().contains(sampleQuestion));
	    }
	    */
	
	@Test
	public void addQuestion() throws JSONException {
		
		String url = createURLWithPort("/surveys/Survey1/questions");
		
		
		
		
		
		
		//sample question, id doesn't matter
		Question sampleQuestion = new Question("DOESNMATTER",
				"Who's the queen of UK", "Elizabeth", Arrays.asList(
						"India", "Elizabeth", "United States", "China"));
		
		//HttpEntity will convert the Question to a JSON
		HttpEntity entity = new HttpEntity<Question>(sampleQuestion, headers);
		//needs a body since it's a POST request
		
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
		//it's POST, we don't get the body but the URI of the created question
		//the SurveyController set a header called location and return the URI of the created question
		String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);		
		
		System.out.println(actual);
		
		//we check the substring because the id of the POST'd question is a random string
		//such as http://localhost:11369/surveys/Survey1/questions/kbbf7dpf02bambmgh3d2k8p4c4
		assertTrue(actual.contains("/surveys/Survey1/questions"));
		
		//String expected = "{\"id\":\"Question1\", \"description\":\"Largest Country in the World\", \"correctAnswer\":\"Russia\"}";
		//JSONAssert.assertEquals(expected, response.getBody(), false);
		
	}


	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
	
	
	

}

//part1: Initialiaze and launch Spring Boot Application
// @RunWith(SpringRunner.class)
// @SpringBootTest(classes= Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @LocalServerPort
// private int port;

//we can have multiple applications we are testing a continuos integration server
//makes sure to avoid conflicts?

//part2: invoce url surveys/Survey1/questions/Question1
// private TestRestTemplate template = new TestRestTemplate();
