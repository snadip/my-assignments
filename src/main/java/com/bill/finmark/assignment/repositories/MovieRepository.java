package com.bill.finmark.assignment.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bill.finmark.assignment.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
	
	@Query(value = "SELECT * FROM movie WHERE movie_name=?",nativeQuery = true)
	public Movie findMovieByName(String movieName);
	
	@Query(value = "SELECT * FROM movie WHERE movie_name=? and movie_release_dt=?",nativeQuery = true)
	public Movie findMovieByNameDate(String movieName,Date movieReleaseDt);
	
	@Query(value = "SELECT * FROM movie WHERE movie_name LIKE %:movieName%",nativeQuery = true)
	public List<Movie> findMovieLike(@Param("movieName") String movieName);
}
