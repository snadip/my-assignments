package com.bill.finmark.assignment.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.bill.finmark.assignment.exceptions.ApplicationCustomException;
import com.bill.finmark.assignment.model.Actor;
import com.bill.finmark.assignment.repositories.ActorRepository;
import com.bill.finmark.assignment.services.ActorService;
import com.bill.finmark.assignment.util.ApplicationConstants;

@Service
@CacheConfig(cacheNames = { "actors" })
public class ActorServiceImpl implements ActorService{

	@Autowired
    private ActorRepository actorRepository;
	
	@Override
	public Actor saveActor(Actor actor) {
		if(findActorByName(actor.getActorName())!=null) {
			throw new ApplicationCustomException(ApplicationConstants.ACTOR_DUPLICATE_ERROR_MSG+actor.getActorName());
		}
		return actorRepository.save(actor);
	}

	@Override
	public List<Actor> saveActors(List<Actor> actors) {
		for(Actor actor:actors) {
			if(findActorByName(actor.getActorName())!=null) {
				throw new ApplicationCustomException(ApplicationConstants.ACTOR_DUPLICATE_ERROR_MSG+actor.getActorName());
			}	
		}
		return actorRepository.saveAll(actors);
	}

	@Override
	@CachePut(cacheNames = "actors", key = "#actor.actorID")
	public Actor updateActor(Actor actor) {
		Actor foundActor=actorRepository.findById(actor.getActorID()).orElseThrow(() -> new ApplicationCustomException(ApplicationConstants.ACTOR_NOTFOUND_ERROR_MSG+actor.getActorID()));
		if(foundActor==null) {
			throw new ApplicationCustomException("Actor not exists" + actor.getActorName());
		}
		return actorRepository.save(actor);
	}

	@Override
	public List<Actor> updateActors(List<Actor> actors) {
		List<Actor> updatedActors=new ArrayList<>();
		for(Actor actor:actors) {
			Actor foundActor=actorRepository.findById(actor.getActorID()).orElseThrow(() -> new ApplicationCustomException(ApplicationConstants.ACTOR_NOTFOUND_ERROR_MSG+actor.getActorID()));
			if(findActorByName(foundActor.getActorName())!=null) {
				throw new ApplicationCustomException(ApplicationConstants.ACTOR_DUPLICATE_ERROR_MSG+foundActor.getActorName());
			}
			updatedActors.add(updateActor(foundActor));
		}
		return updatedActors;
	}

	@Override
	@CacheEvict(value = "actors", key = "#id")
	public void deleteActorByID(int id) {
		Actor actor=getActorByID(id);
		if(actor.getMovies()!=null&&!actor.getMovies().isEmpty()) {
			throw new ApplicationCustomException(ApplicationConstants.ACTOR_DELETE_ERROR_MSG);
		}
		actorRepository.delete(actor);
	}

	@Override
	@CacheEvict(value = "actors", key = "#name")
	public void deleteActorByName(String name) {
		Actor actor=findActorByName(name);
		if(actor==null) {
			throw new ApplicationCustomException(ApplicationConstants.ACTOR_NOTFOUND_ERROR_MSG+name);
		}
		
		if(actor.getMovies()!=null&&!actor.getMovies().isEmpty()) {
			throw new ApplicationCustomException(ApplicationConstants.ACTOR_DELETE_ERROR_MSG);
		}
		actorRepository.delete(actor);
		
	}
	
	@Override
	public void deleteActors(List<Actor> actors) {
		for(Actor actor:actors) {
			if(actor.getMovies()!=null&&!actor.getMovies().isEmpty()) {
				throw new ApplicationCustomException(ApplicationConstants.ACTOR_DELETE_ERROR_MSG);
			}
			deleteActorByID(actor.getActorID());
		}
		
		
	}

	@Override
	@Cacheable(value = "actors", key = "#id")
	public Actor getActorByID(int id) {
		return actorRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(ApplicationConstants.ACTOR_NOTFOUND_ERROR_MSG+id));
	}

	@Override
	@Query(value = "SELECT * FROM actor WHERE actorName=?",nativeQuery = true)
	@Cacheable(value = "actors", key = "#actorName")
	public Actor findActorByName(String actorName) {
		return actorRepository.findActorByName(actorName);
	}

	@Override
	public List<Actor> findActorNameLike(String actorName) {
		List<Actor> actors=actorRepository.findActorLike(actorName);
		if(actors==null || actors.size()==0) {
			throw new ApplicationCustomException(ApplicationConstants.ACTOR_NOTFOUND_ERROR_MSG+actorName);
		}
		return actors;
	}

}
