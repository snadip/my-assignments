package com.bill.finmark.assignment.services;

import java.sql.Date;
import java.util.List;

import com.bill.finmark.assignment.model.Movie;

public interface MovieService {
	
	public Movie saveMovie(Movie movie);
	public List<Movie> saveMovies(List<Movie> movies);
	
	public Movie updateMovie(Movie movie);
	public List<Movie> updateMovies(List<Movie> movies);
	
	public void deleteMovieByID(int id);
	public void deleteMovieByName(String movieName);
	public void deleteMovies(List<Movie> movies);
	
	public Movie getMovieByID(int id);
	public Movie getMovieByName(String movieName);
	public Movie getMovieByNameDate(String movieName,Date movieReleaseDt);
	public List<Movie> getMoviesLike(String movieName);

}
