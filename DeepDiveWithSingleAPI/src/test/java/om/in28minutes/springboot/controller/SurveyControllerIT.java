/* see part 2 of application
 * 

package com.in28minutes.springboot.controller;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
//import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.in28minutes.springboot.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyControllerIT {

	@Test
	public void test() {
		//fail("Not yet implemented");
	}

}

//part 1: initialize and launch Spring Boot application

//part 2: invoke the url /surveys/Survey1/questions/Question1
// does itbehave as expected?

*/