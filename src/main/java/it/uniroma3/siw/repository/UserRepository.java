package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.User;

import java.util.Set;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE users.id IN (SELECT author_id FROM review WHERE movie_id = :movieId)", nativeQuery = true)
    public Set<User> findAllReviewAuthorsByMovie(Long movieId);
}