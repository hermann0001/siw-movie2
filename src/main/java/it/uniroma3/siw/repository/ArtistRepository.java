package it.uniroma3.siw.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Artist;

import java.util.Set;

public interface ArtistRepository extends CrudRepository<Artist, Long> {

	public boolean existsByNameAndSurname(String name, String surname);	

	@Query(value="select * "
			+ "from artist a "
			+ "where a.id not in "
			+ "(select actors_id "
			+ "from movie_actors "
			+ "where movie_actors.starred_movies_id = :movieId)", nativeQuery=true)
	public Iterable<Artist> findActorsNotInMovie(@Param("movieId") Long id);

	@Query(value="SELECT * FROM artist WHERE artist.id NOT IN (SELECT director_id FROM movie WHERE movie.id = :movieId AND director_id IS NOT NULL)", nativeQuery = true)
	public Iterable<Artist> findAllDirectorsNotInMovie(@Param("movieId") Long id);


}