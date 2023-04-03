package com.bill.finmark.assignment.services.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.bill.finmark.assignment.exceptions.ApplicationCustomException;
import com.bill.finmark.assignment.model.Actor;
import com.bill.finmark.assignment.model.Movie;
import com.bill.finmark.assignment.repositories.MovieRepository;
import com.bill.finmark.assignment.services.MovieService;
import com.bill.finmark.assignment.util.ApplicationConstants;

@Service
@CacheConfig(cacheNames = { "movies" })
public class MovieServiceImpl implements MovieService{

	@Autowired
    private MovieRepository movieRepository;
	
	@Override
	public Movie saveMovie(Movie movie) {
		if(movie.getActors()==null || movie.getActors().isEmpty()) {
			throw new ApplicationCustomException(ApplicationConstants.MOVIE_NOACTORS_ERROR_MSG);
		}
		
		Movie movie2=getMovieByNameDate(movie.getMovieName(), movie.getMovieReleaseDate());
		if(movie2!=null){
			throw new ApplicationCustomException(ApplicationConstants.MOVIE_DUPLICATE_ERROR_MSG);
		}
		
		Set<Actor> uniqueActors=new HashSet<>(movie.getActors());
		if(uniqueActors.size()!=movie.getActors().size()) {
			throw new ApplicationCustomException(ApplicationConstants.MOVIE_DUPLICATE_ACTORS_ERROR_MSG);
		}
		
		return movieRepository.save(movie);
	}

	@Override
	public List<Movie> saveMovies(List<Movie> movies) {
		for(Movie movie:movies) {
			if(movie.getActors()==null || movie.getActors().isEmpty()) {
				throw new ApplicationCustomException(ApplicationConstants.MOVIE_NOACTORS_ERROR_MSG);
			}
			if(getMovieByNameDate(movie.getMovieName(), movie.getMovieReleaseDate())!=null){
				throw new ApplicationCustomException(ApplicationConstants.MOVIE_DUPLICATE_ERROR_MSG);
			}
			
			Set<Actor> uniqueActors=new HashSet<>(movie.getActors());
			if(uniqueActors.size()!=movie.getActors().size()) {
				throw new ApplicationCustomException(ApplicationConstants.MOVIE_DUPLICATE_ACTORS_ERROR_MSG);
			}
		}
		return movieRepository.saveAll(movies);
	}

	@Override
	@CachePut(cacheNames = "movies", key = "#movie.movieID")
	public Movie updateMovie(Movie movie) {
		Movie beforeUpdateMovie=movieRepository.findById(movie.getMovieID()).orElseThrow(() -> new ApplicationCustomException(ApplicationConstants.MOVIE_NOTFOUND_ERROR_MSG));
		if(movie.getActors()==null || movie.getActors().isEmpty()) {
			throw new ApplicationCustomException(ApplicationConstants.MOVIE_NOACTORS_ERROR_MSG);
		}
		
		Set<Actor> uniqueActors=new HashSet<>(movie.getActors());
		if(uniqueActors.size()!=movie.getActors().size()) {
			throw new ApplicationCustomException(ApplicationConstants.MOVIE_DUPLICATE_ACTORS_ERROR_MSG);
		}
		
		for(Actor actor:movie.getActors()) {
			if(!beforeUpdateMovie.getActors().contains(actor)) {
				beforeUpdateMovie.getActors().add(actor);
			}
		}
		
		if(!beforeUpdateMovie.getMovieName().equals(movie.getMovieName())) {
			beforeUpdateMovie.setMovieName(movie.getMovieName());
		}
		
		if(beforeUpdateMovie.getMovieReleaseDate().compareTo(movie.getMovieReleaseDate())!=0){
			beforeUpdateMovie.setMovieReleaseDate(movie.getMovieReleaseDate());
		}
		
		return movieRepository.save(beforeUpdateMovie);
	}

	@Override
	public List<Movie> updateMovies(List<Movie> movies) {
		List<Movie> updatedMovies=new ArrayList<>();
		for(Movie movie:movies) {
			updatedMovies.add(updateMovie(movie));
		}
		return updatedMovies;
	}

	@Override
	@CacheEvict(value = "movies", key = "#id")
	public void deleteMovieByID(int id) {
		Movie movie=movieRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(ApplicationConstants.MOVIE_NOTFOUND_ERROR_MSG + id));
		movieRepository.delete(movie);	
	}

	@Override
	public void deleteMovies(List<Movie> movies) {
		for(Movie movie:movies) {
			deleteMovieByID(movie.getMovieID());
		}
	}

	@Override
	@Cacheable(value = "movies", key = "{#id}")
	public Movie getMovieByID(int id) {
		return movieRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(ApplicationConstants.MOVIE_NOTFOUND_ERROR_MSG + id));
	}

	@Override
	@Cacheable(value = "movies", key = "{#movieName}")
	public Movie getMovieByName(String movieName) {
		return movieRepository.findMovieByName(movieName);
	}

	@Override
	@CacheEvict(value = "movies", key = "#movieName")
	public void deleteMovieByName(String movieName) {
		Movie movie=movieRepository.findMovieByName(movieName);
		if(movie==null) {
			throw new ApplicationCustomException(ApplicationConstants.MOVIE_NOTFOUND_ERROR_MSG + movieName);
		}
		movieRepository.delete(movie);	
	}

	@Override
	public List<Movie> getMoviesLike(String movieName) {
		return movieRepository.findMovieLike(movieName);
	}

	@Override
	@Cacheable(value = "movies", key = "{#movieName,#movieReleaseDt}")
	public Movie getMovieByNameDate(String movieName, Date movieReleaseDt) {
		Movie movie=movieRepository.findMovieByNameDate(movieName,movieReleaseDt);
		return movie;
	}

}
