package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Movie;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends CrudRepository<Movie, Long> {

	public List<Movie> findByYear(int year);

	public boolean existsByTitleAndYear(String title, int year);

	@Query(value="SELECT * FROM movie WHERE director_id <> :artistId OR director_id IS NULL", nativeQuery = true)
	public Iterable<Movie> findMoviesNotDirectedByArtist(@Param("artistId") Long id);

	@Query(value="SELECT * FROM movie WHERE movie.id NOT IN (SELECT starred_movies_id FROM movie_actors WHERE actors_id = :artistId)", nativeQuery = true)
	public Iterable<Movie> findMoviesNotStarredByArtist(@Param("artistId") Long id);
}