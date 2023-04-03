package com.bill.finmark.assignment.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table
public class Actor implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    @Column(name = "actor_id")
	private int actorID;
	
	@NotEmpty
	@Column(name = "actor_name")
	private String actorName;

	@Override
	public String toString() {
		return "Actor [actorID=" + actorID + ", actorName=" + actorName + "]";
	}

	public int getActorID() {
		return actorID;
	}

	public void setActorID(int actorID) {
		this.actorID = actorID;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}
	
	@JsonBackReference
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "movie_cast")
    private List<Movie> movies = new ArrayList<>();

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
	
	public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (null == actorName ? 0 : actorName.hashCode());
        return hash;
    }
	
	public boolean equals(Object obj) {
        if (this == obj) return true;
        if ((obj == null) || (obj.getClass() != this.getClass())) return false;
        Actor actor = (Actor) obj;
        return actorName == actor.actorName || (actorName != null && actorName.equals(actor.actorName));
    }

}
