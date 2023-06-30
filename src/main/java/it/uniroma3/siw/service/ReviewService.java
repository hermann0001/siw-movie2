package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.MovieRepository;
import it.uniroma3.siw.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    MovieService movieService;

    @Transactional
    public Review saveReview(Review review, Movie movie, User author){
        movie.addReview(review);
        review.setMovie(movie);
        review.setAuthor(author);

        return this.reviewRepository.save(review);
    }

    public boolean exists(Review review) {
        return this.reviewRepository.existsByMovieAndAuthor(review.getMovie(), review.getAuthor());
    }
}
