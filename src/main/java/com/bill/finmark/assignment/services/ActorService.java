package com.bill.finmark.assignment.services;

import java.util.List;

import com.bill.finmark.assignment.model.Actor;

public interface ActorService {
	
	public Actor saveActor(Actor actor);
	public List<Actor> saveActors(List<Actor> actors);
	
	public Actor updateActor(Actor actor);
	public List<Actor> updateActors(List<Actor> actors);
	
	public void deleteActorByID(int id);
	public void deleteActorByName(String name);
	public void deleteActors(List<Actor> actors);
	
	public Actor getActorByID(int id);
	public Actor findActorByName(String actorName);
	public List<Actor> findActorNameLike(String actorName);

}
