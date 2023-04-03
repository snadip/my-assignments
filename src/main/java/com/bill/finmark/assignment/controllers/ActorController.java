package com.bill.finmark.assignment.controllers;

import java.util.List;

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

import com.bill.finmark.assignment.model.Actor;
import com.bill.finmark.assignment.services.ActorService;

import jakarta.validation.Valid;

@RestController
public class ActorController {

	@Autowired
    private ActorService actorService;
	
	@GetMapping(value="/getActorByID/{id}",produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Actor> getActorByID(@PathVariable int id) {
		Actor actor=actorService.getActorByID(id);
        return ResponseEntity.ok().body(actor);
    }
	
	@GetMapping(value="/getActorByName/{name}",produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Actor> getActorName(@PathVariable String name) {
		Actor actor=actorService.findActorByName(name);
        return ResponseEntity.ok().body(actor);
    }
	
	@GetMapping(value="/getActorLike/{name}",produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Actor>> getActorNameLike(@PathVariable String name) {
		List<Actor> actors=actorService.findActorNameLike(name);
        return ResponseEntity.ok().body(actors);
    }
	
	@PostMapping(value="/addActor",consumes = MediaType.APPLICATION_JSON_VALUE,produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Actor> addActor(@Valid @RequestBody Actor actor) {
		return new ResponseEntity<Actor>(actorService.saveActor(actor), HttpStatus.CREATED);
    }
	
	@PostMapping(value="/addActors",consumes = MediaType.APPLICATION_JSON_VALUE,produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Actor>> addActor(@Valid @RequestBody List<Actor> actors) {
		return new ResponseEntity<List<Actor>>(actorService.saveActors(actors), HttpStatus.CREATED);
    }
	
	@PutMapping(value="/updateActor",consumes = MediaType.APPLICATION_JSON_VALUE,produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Actor> updateActor(@Valid @RequestBody Actor actor) {
		return new ResponseEntity<Actor>(actorService.updateActor(actor), HttpStatus.CREATED);
    }
	
	@PutMapping(value="/updateActors",consumes = MediaType.APPLICATION_JSON_VALUE,produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Actor>> updateActors(@Valid @RequestBody List<Actor> actors) {
		return new ResponseEntity<List<Actor>>(actorService.updateActors(actors), HttpStatus.CREATED);
    }
	
	@DeleteMapping(value="/deleteActorByID/{id}",produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteActorByID(@PathVariable int id) {
		actorService.deleteActorByID(id);
        return ResponseEntity.ok().body("Actor deleted successfully");
    }
	
	@DeleteMapping(value="/deleteActorByName/{name}",produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteActorByName(@PathVariable String name) {
		actorService.deleteActorByName(name);
        return ResponseEntity.ok().body("Actor deleted successfully");
    }
	
	@DeleteMapping(value="/deleteActors",produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteActors(@RequestBody List<Actor> actors) {
		actorService.deleteActors(actors);
        return ResponseEntity.ok().body("Actors deleted successfully");
    }
	
}
