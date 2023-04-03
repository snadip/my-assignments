package com.bill.finmark.assignment.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Movie implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "movie_id")
	private int movieID;

	@NotEmpty
	@Column(name = "movie_name")
	private String movieName;

	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name = "movie_release_dt")
	private Date movieReleaseDate;

	@ManyToMany(mappedBy = "movies", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Actor> actors;

	public int getMovieID() {
		return movieID;
	}

	public void setMovieID(int movieID) {
		this.movieID = movieID;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public Date getMovieReleaseDate() {
		return movieReleaseDate;
	}

	public void setMovieReleaseDate(Date movieReleaseDate) {
		this.movieReleaseDate = movieReleaseDate;
	}

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}

	@Override
	public String toString() {
		return "Movie [movieID=" + movieID + ", movieName=" + movieName + ", movieReleaseDate=" + movieReleaseDate
				+ ", actors=" + actors + "]";
	}
	
	public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (null == movieName || null == movieReleaseDate ? 0 : movieName.hashCode()+movieReleaseDate.hashCode());
        return hash;
    }
	
	public boolean equals(Object obj) {
        if (this == obj) return true;
        if ((obj == null) || (obj.getClass() != this.getClass())) return false;
        Movie movie = (Movie) obj;
        String key=movieName.concat(movieReleaseDate.toString());
        String objKey=movie.getMovieName().concat(movie.getMovieReleaseDate().toString());
        return key == objKey;
    }

}
