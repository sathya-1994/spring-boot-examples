package com.in28minutes.springboot.controller;

import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.junit4.SpringRunner;

import com.in28minutes.springboot.Application;
import com.in28minutes.springboot.model.Question;

@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SurveyControllerIT {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	@Before(value = "")
	public void before() {

	}

	@Test
	void testJsonAssert() throws JSONException {
		JSONAssert.assertEquals("{id:1}", "{id:1 , name:Ranga}", false);

	}

	@Test
	void test() throws JSONException {

		String url = "http://localhost:" + port + "/surveys/Survey1/questions/Question1";
		TestRestTemplate restTemplate = new TestRestTemplate();
//		String response = restTemplate.getForObject(url, String.class);
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", createHttpAuthorizationHeaderValue("user1", "secret1"));

		header.setAccept(Arrays.asList(org.springframework.http.MediaType.APPLICATION_JSON));

		@SuppressWarnings("rawtypes")
		HttpEntity entity = new HttpEntity<>(null, header);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		String expectedStr = "{\"id\":\"Question1\",\"description\":\"Largest Country in the World\",\"correctAnswer\":\"Russia\",\"options\":[\"India\",\"Russia\",\"United States\",\"China\"]}";
		System.out.println(expectedStr);
		System.out.println(response.getBody());

		JSONAssert.assertEquals(expectedStr, response.getBody(), false);
	}

	private String createHttpAuthorizationHeaderValue(String username, String password) {
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.encode(auth.getBytes(Charset.forName("US-ASCII")));
		String authHeader = "Basic " + new String(encodedAuth);
		// "Authorization Basic BaseEncoding(userId + ":" + password)
		return authHeader;
	}

	@Test
	public void retrieveAllSurveyQuestions() throws Exception {

		String url = createURLWithPort();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", createHttpAuthorizationHeaderValue("user1", "secret1"));
		headers.setAccept(Arrays.asList(org.springframework.http.MediaType.APPLICATION_JSON));

		ResponseEntity<List<Question>> response = restTemplate.exchange(url, HttpMethod.GET,
				new HttpEntity<String>("DUMMY_DOESNT_MATTER", headers),
				new ParameterizedTypeReference<List<Question>>() {
				});

		Question sampleQuestion = new Question("Question1", "Largest Country in the World", "Russia",
				Arrays.asList("India", "Russia", "United States", "China"));

		assertTrue(response.getBody().contains(sampleQuestion));
	}

	@Test
	public void createSurveyQuestion() throws Exception {

		String url = createURLWithPort();
		Question question = new Question("DOESN'T MATTER", "Smallest Number", "1", Arrays.asList("1", "2", "3", "4"));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", createHttpAuthorizationHeaderValue("user1", "secret1"));
		headers.setAccept(Arrays.asList(org.springframework.http.MediaType.APPLICATION_JSON));

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
				new HttpEntity<Question>(question, headers), String.class);
		String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
		assertTrue(actual.contains("/surveys/Survey1/questions/"));
	}

	private String createURLWithPort() {
		return "http://localhost:" + port + "/surveys/Survey1/questions";
	}
}
