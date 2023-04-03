INSERT INTO MOVIE (movie_id,movie_name, movie_release_dt) VALUES (101,'Movie 101','2021-09-13');
INSERT INTO MOVIE (movie_id,movie_name, movie_release_dt) VALUES (102,'Movie 102','2020-09-14');
INSERT INTO MOVIE (movie_id,movie_name, movie_release_dt) VALUES (103,'Movie 103','2019-09-15');


INSERT INTO ACTOR (actor_id,actor_name) VALUES (101,'Actor 101');
INSERT INTO ACTOR (actor_id,actor_name) VALUES (102,'Actor 102');
INSERT INTO ACTOR (actor_id,actor_name) VALUES (103,'Actor 103');
INSERT INTO ACTOR (actor_id,actor_name) VALUES (104,'Actor 104');
INSERT INTO ACTOR (actor_id,actor_name) VALUES (105,'Actor 105');
INSERT INTO ACTOR (actor_id,actor_name) VALUES (106,'Actor 106');

INSERT INTO MOVIE_CAST(cast_id,movies_movie_id,actors_actor_id) VALUES (101,101,101);
INSERT INTO MOVIE_CAST(cast_id,movies_movie_id,actors_actor_id) VALUES (102,101,102);
INSERT INTO MOVIE_CAST(cast_id,movies_movie_id,actors_actor_id) VALUES (103,101,103);

INSERT INTO MOVIE_CAST(cast_id,movies_movie_id,actors_actor_id) VALUES (104,102,104);
INSERT INTO MOVIE_CAST(cast_id,movies_movie_id,actors_actor_id) VALUES (105,102,105);

INSERT INTO MOVIE_CAST(cast_id,movies_movie_id,actors_actor_id) VALUES (106,103,101);
INSERT INTO MOVIE_CAST(cast_id,movies_movie_id,actors_actor_id) VALUES (107,103,102);
INSERT INTO MOVIE_CAST(cast_id,movies_movie_id,actors_actor_id) VALUES (108,103,103);
INSERT INTO MOVIE_CAST(cast_id,movies_movie_id,actors_actor_id) VALUES (109,103,104);
INSERT INTO MOVIE_CAST(cast_id,movies_movie_id,actors_actor_id) VALUES (110,103,105);

