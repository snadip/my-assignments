package com.bill.finmark.assignment.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bill.finmark.assignment.model.Movie;
import com.bill.finmark.assignment.services.MovieService;

import jakarta.validation.Valid;


@RestController
public class MovieController {

	Logger logger = LoggerFactory.getLogger(MovieController.class);
	
	@Autowired
    private MovieService movieService;
	
	@GetMapping(value="/getMovieByID/{id}",produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> getMovieByID(@PathVariable int id) {
		Movie movie=movieService.getMovieByID(id);
		logger.info("Movie : "+movie.toString());
        return ResponseEntity.ok().body(movie);
    }
	
	@GetMapping(value="/getMovieByName/{name}",produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMovieByName(@PathVariable String name) {
        Movie movie = movieService.getMovieByName(name);
        return ResponseEntity.ok().body(movie);
    }
	
	@GetMapping(value="/getMoviesLike/{name}",produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Movie>> getMoviesLike(@PathVariable String name) {
		List<Movie> movies = movieService.getMoviesLike(name);
        return ResponseEntity.ok().body(movies);
    }
	
	@PostMapping(value="/addMovie",consumes = MediaType.APPLICATION_JSON_VALUE,produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> addMovie(@Valid @RequestBody Movie movie) {
		return new ResponseEntity<Movie>(movieService.saveMovie(movie), HttpStatus.CREATED);
    }
	
	@PostMapping(value="/addMovies",produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Movie>> addMovies(@RequestBody List<Movie> movies) {
		return new ResponseEntity<List<Movie>>(movieService.saveMovies(movies), HttpStatus.CREATED);
    }
	
	@PutMapping(value="/updateMovie",produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie) {
        return ResponseEntity.ok().body(movieService.updateMovie(movie));
    }
	
	@PutMapping(value="/updateMovies",produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Movie>> updateMovies(@RequestBody List<Movie> movies) {
        return ResponseEntity.ok().body(movieService.updateMovies(movies));
    }
	
	@DeleteMapping(value="/deleteMovieByID/{id}",produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteMovieByID(@PathVariable int id) {
		movieService.deleteMovieByID(id);
        return ResponseEntity.ok().body("Movie deleted successfully");
    }
	
	@DeleteMapping(value="/deleteMovieByName/{name}",produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteMovieByName(@PathVariable String name) {
		movieService.deleteMovieByName(name);
        return ResponseEntity.ok().body("Movie deleted successfully");
    }
	
	@DeleteMapping(value="/deleteMovies",produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteMovies(@RequestBody List<Movie> movies) {
		movieService.deleteMovies(movies);
        return ResponseEntity.ok().body("Movies deleted successfully");
    }
	
	
}
