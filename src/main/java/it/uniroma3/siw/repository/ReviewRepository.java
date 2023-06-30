package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    public boolean existsByMovieAndAuthor(Movie movie, User author);

    public Iterable<Review> findAllByAuthor(User author);
    public Iterable<Review> findAllByMovie(Movie movie);
}
