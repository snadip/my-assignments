package com.bill.finmark.assignment.util;

public interface ApplicationConstants {
	
	public static final String ACTOR_DUPLICATE_ERROR_MSG = "Actor already exists ";
	public static final String ACTOR_DELETE_ERROR_MSG = "An Actor cannot be deleted if they are a part of any movie(s)";
	public static final String ACTOR_NOTFOUND_ERROR_MSG = "Actor not found ";
	
	public static final String MOVIE_DUPLICATE_ERROR_MSG = "Movie title and release date already present";
	public static final String MOVIE_NOACTORS_ERROR_MSG = "Movie must have at least one actor";
	public static final String MOVIE_NOTFOUND_ERROR_MSG = "No movies found ";
	public static final String MOVIE_DUPLICATE_ACTORS_ERROR_MSG = "actor(s) can not be duplicated for the the same movie";

}
