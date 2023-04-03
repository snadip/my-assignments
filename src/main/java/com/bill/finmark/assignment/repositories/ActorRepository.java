package com.bill.finmark.assignment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bill.finmark.assignment.model.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
	
	@Query(value = "SELECT * FROM actor WHERE actor_name=?",nativeQuery = true)
	public Actor findActorByName(String actorName);
	
	@Query(value = "SELECT * FROM actor WHERE actor_name LIKE %:actorName%",nativeQuery = true)
	public List<Actor> findActorLike(@Param("actorName") String actorName);
	
}
