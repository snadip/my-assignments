package com.bill.finmark.assignment.actor.test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import com.bill.finmark.assignment.FinMarkMovieCrudApplication;
import com.bill.finmark.assignment.controllers.MovieController;
import com.bill.finmark.assignment.exceptions.GenericErrorRespose;
import com.bill.finmark.assignment.model.Actor;
import com.bill.finmark.assignment.model.Movie;
import com.bill.finmark.assignment.util.ApplicationConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = FinMarkMovieCrudApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql({ "classpath:test_schema.sql", "classpath:test_data.sql" })
public class MovieIntegrationTest {

	Logger logger = LoggerFactory.getLogger(MovieController.class);
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void testGetMovieByID() throws JsonMappingException, JsonProcessingException {
		String endPoint="http://localhost:" + port + "/getMovieByID/101";
		Movie movie = mapper.readValue(restTemplate.getForObject(endPoint, String.class), Movie.class);
		logger.info("Movie "+movie.getMovieID());
		assertEquals(movie.getMovieID(),101);
	}
	
	@Test
	public void testGetMovieName() throws JsonMappingException, JsonProcessingException {
		String endPoint="http://localhost:" + port + "/getMovieByName/Movie 102";
		Movie movie = mapper.readValue(restTemplate.getForObject(endPoint, String.class), Movie.class);
		logger.info("Movie "+movie.getMovieName());
		assertEquals(movie.getMovieName(),"Movie 102");
	}
	
	@Test
	public void testAddMovie() throws JsonMappingException, JsonProcessingException {
		String endPoint="http://localhost:" + port + "/addMovie";
		Movie movie=new Movie();
		movie.setMovieName("MovieTest1");
		movie.setMovieReleaseDate(Date.valueOf("2020-04-20"));
		Actor actor=new Actor();
		actor.setActorName("ActorTest");
		List<Actor> actors=new ArrayList<>();
		actors.add(actor);
		movie.setActors(actors);
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		String jsonString = mapper.writeValueAsString(movie);
		HttpEntity<String> request = new HttpEntity<String>(jsonString, headers);
		Movie movie2 = restTemplate.postForObject(endPoint, request, Movie.class);
		logger.info("Movie "+movie2.getMovieName());
		assertEquals(movie.getMovieName(),"MovieTest1");
	}
	
	@Test
	public void testAddDuplicateMovie() throws JsonMappingException, JsonProcessingException, ParseException {
		String endPoint="http://localhost:" + port + "/addMovie";
		Movie movie=new Movie();
		movie.setMovieName("Movie 101");
		String strDate = "2021-09-14";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf.parse(strDate); 
        java.sql.Date sqlDate = new Date(date.getTime()); 
		movie.setMovieReleaseDate(sqlDate);
		Actor actor=new Actor();
		actor.setActorName("Actor 101");
		List<Actor> actors=new ArrayList<>();
		actors.add(actor);
		movie.setActors(actors);
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		String jsonString = mapper.writeValueAsString(movie);
		HttpEntity<String> request = new HttpEntity<String>(jsonString, headers);
		GenericErrorRespose errorRespose = restTemplate.postForObject(endPoint, request, GenericErrorRespose.class);
		logger.info("Movie "+errorRespose.getMessage());
		assertEquals(errorRespose.getMessage(),ApplicationConstants.MOVIE_DUPLICATE_ERROR_MSG);
	}
	
	@Test
	public void testMovieWithNoActors() throws JsonMappingException, JsonProcessingException {
		String endPoint="http://localhost:" + port + "/addMovie";
		Movie movie=new Movie();
		movie.setMovieName("MovieTest2");
		movie.setMovieReleaseDate(Date.valueOf("2020-04-20"));
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		String jsonString = mapper.writeValueAsString(movie);
		HttpEntity<String> request = new HttpEntity<String>(jsonString, headers);
		GenericErrorRespose errorRespose = restTemplate.postForObject(endPoint, request, GenericErrorRespose.class);
		logger.info("Movie "+errorRespose.getMessage());
		assertEquals(errorRespose.getMessage(),ApplicationConstants.MOVIE_NOACTORS_ERROR_MSG);
	}
	
	@Test
	public void testMovieWithDuplicateActors() throws JsonMappingException, JsonProcessingException {
		String endPoint="http://localhost:" + port + "/addMovie";
		Movie movie=new Movie();
		movie.setMovieName("MovieTest2");
		movie.setMovieReleaseDate(Date.valueOf("2020-04-20"));
		List<Actor> actors=new ArrayList<>();
		Actor actor=new Actor();
		actor.setActorName("Actor 101");
		actors.add(actor);
		actor=new Actor();
		actor.setActorName("Actor 101");
		actors.add(actor);
		movie.setActors(actors);
		logger.info("Actors"+actors);
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		String jsonString = mapper.writeValueAsString(movie);
		HttpEntity<String> request = new HttpEntity<String>(jsonString, headers);
		GenericErrorRespose errorRespose = restTemplate.postForObject(endPoint, request, GenericErrorRespose.class);
		logger.info("Movie "+errorRespose.getMessage());
		assertEquals(errorRespose.getMessage(),ApplicationConstants.MOVIE_DUPLICATE_ACTORS_ERROR_MSG);
	}
	
	@Test
	public void testUpdateMovie() throws JsonMappingException, JsonProcessingException {
		String endPoint="http://localhost:" + port + "/updateMovie";
		String endPointGet="http://localhost:" + port + "/getMovieByID/102";
		Movie movie = mapper.readValue(restTemplate.getForObject(endPointGet, String.class), Movie.class);
		movie.setMovieName("MovieUpdated");
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		String jsonString = mapper.writeValueAsString(movie);
		HttpEntity<String> request = new HttpEntity<String>(jsonString, headers);
		restTemplate.put(endPoint, request);
		String endPointName="http://localhost:" + port + "/getMovieByName/MovieUpdated";
		Movie movieUpdated = mapper.readValue(restTemplate.getForObject(endPointName, String.class), Movie.class);
		logger.info("Movie "+movieUpdated.getMovieName());
		assertEquals(movieUpdated.getMovieName(),"MovieUpdated");
	}
	
	@Test
	public void testDeleteMovie() throws JsonMappingException, JsonProcessingException {
		String endPoint="http://localhost:" + port + "/deleteMovieByID/103";
		String endPointGet="http://localhost:" + port + "/getMovieByID/103";
		restTemplate.delete(endPoint);
		GenericErrorRespose errorRespose = mapper.readValue(restTemplate.getForObject(endPointGet, String.class), GenericErrorRespose.class);
		logger.info("Deleted Movie "+errorRespose.getMessage());
		assertEquals(errorRespose.getMessage(),ApplicationConstants.MOVIE_NOTFOUND_ERROR_MSG+"103");
	}
	
}
