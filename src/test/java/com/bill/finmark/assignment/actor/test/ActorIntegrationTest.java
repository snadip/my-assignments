package com.bill.finmark.assignment.actor.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.bill.finmark.assignment.FinMarkMovieCrudApplication;
import com.bill.finmark.assignment.controllers.ActorController;
import com.bill.finmark.assignment.exceptions.GenericErrorRespose;
import com.bill.finmark.assignment.model.Actor;
import com.bill.finmark.assignment.util.ApplicationConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = FinMarkMovieCrudApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql({ "classpath:test_schema.sql", "classpath:test_data.sql" })
public class ActorIntegrationTest {

	Logger logger = LoggerFactory.getLogger(ActorController.class);

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testGetActorByID() throws JsonMappingException, JsonProcessingException {
		String endPoint = "http://localhost:" + port + "/getActorByID/101";
		Actor actor = mapper.readValue(restTemplate.getForObject(endPoint, String.class), Actor.class);
		logger.info("Actor " + actor.getActorID());
		assertEquals(actor.getActorID(), 101);
	}

	@Test
	public void testGetActorName() throws JsonMappingException, JsonProcessingException {
		String endPoint = "http://localhost:" + port + "/getActorByName/Actor 102";
		Actor actor = mapper.readValue(restTemplate.getForObject(endPoint, String.class), Actor.class);
		logger.info("Actor " + actor.getActorName());
		assertEquals(actor.getActorName(), "Actor 102");
	}

	@Test
	public void testAddActor() throws JsonMappingException, JsonProcessingException {
		String endPoint = "http://localhost:" + port + "/addActor";
		Actor actor = new Actor();
		actor.setActorName("ActorTest1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String jsonString = mapper.writeValueAsString(actor);
		HttpEntity<String> request = new HttpEntity<String>(jsonString, headers);
		Actor actor2 = restTemplate.postForObject(endPoint, request, Actor.class);
		logger.info("Actor " + actor2.getActorName());
		assertEquals(actor.getActorName(), "ActorTest1");
	}

	@Test
	public void testAddDuplicateActor() throws JsonMappingException, JsonProcessingException, ParseException {
		String endPoint = "http://localhost:" + port + "/addActor";
		Actor actor = new Actor();
		actor.setActorName("Actor 101");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String jsonString = mapper.writeValueAsString(actor);
		HttpEntity<String> request = new HttpEntity<String>(jsonString, headers);
		GenericErrorRespose errorRespose = restTemplate.postForObject(endPoint, request, GenericErrorRespose.class);
		logger.info("Actor " + errorRespose.getMessage());
		assertEquals(errorRespose.getMessage(), ApplicationConstants.ACTOR_DUPLICATE_ERROR_MSG+"Actor 101");
	}

	@Test
	public void testUpdateActor() throws JsonMappingException, JsonProcessingException {
		String endPoint = "http://localhost:" + port + "/updateActor";
		String endPointGet = "http://localhost:" + port + "/getActorByID/102";
		Actor actor = mapper.readValue(restTemplate.getForObject(endPointGet, String.class), Actor.class);
		actor.setActorName("ActorUpdated");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String jsonString = mapper.writeValueAsString(actor);
		HttpEntity<String> request = new HttpEntity<String>(jsonString, headers);
		restTemplate.put(endPoint, request);
		String endPointName = "http://localhost:" + port + "/getActorByName/ActorUpdated";
		Actor actorUpdated = mapper.readValue(restTemplate.getForObject(endPointName, String.class), Actor.class);
		logger.info("Actor " + actorUpdated.getActorName());
		assertEquals(actorUpdated.getActorName(), "ActorUpdated");
	}

	@Test
	public void testDeleteActorWithMovie() throws JsonMappingException, JsonProcessingException {
		String endPoint = "http://localhost:" + port + "/deleteActorByID/103";
		String endPointGet = "http://localhost:" + port + "/getActorByID/103";
		Actor actor = mapper.readValue(restTemplate.getForObject(endPointGet, String.class), Actor.class);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String jsonString = mapper.writeValueAsString(actor);
		HttpEntity<String> request = new HttpEntity<String>(jsonString, headers);
		ResponseEntity<GenericErrorRespose> errorRespose = restTemplate.exchange(endPoint, HttpMethod.DELETE, request,GenericErrorRespose.class);
		logger.info("Deleted Actor " + errorRespose.getBody().getMessage());
		assertEquals(errorRespose.getBody().getMessage(), ApplicationConstants.ACTOR_DELETE_ERROR_MSG);
	}

	@Test
	public void testDeleteActorWithNoMovies() throws JsonMappingException, JsonProcessingException {
		String endPoint = "http://localhost:" + port + "/deleteActorByID/106";
		String endPointGet = "http://localhost:" + port + "/getActorByID/106";
		restTemplate.delete(endPoint);
		GenericErrorRespose errorRespose = mapper.readValue(restTemplate.getForObject(endPointGet, String.class),GenericErrorRespose.class);
		logger.info("Deleted Actor " + errorRespose.getMessage());
		assertEquals(errorRespose.getMessage(), ApplicationConstants.ACTOR_NOTFOUND_ERROR_MSG+"106");
	}
}
