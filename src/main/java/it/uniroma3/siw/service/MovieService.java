package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ArtistService artistService;

    @Transactional
    public Movie saveMovie(Movie movie) {
        return this.movieRepository.save(movie);
    }

    public Movie findMovie(Long id) {
        return this.movieRepository.findById(id).orElse(null);
    }

    public Iterable<Movie> findMovies(int year){
        return this.movieRepository.findByYear(year);
    }

    public Iterable<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }

    public boolean exists(Movie movie){
        return movie.getTitle() != null && this.movieRepository.existsByTitleAndYear(movie.getTitle(), movie.getYear());
    }

    public Iterable<Movie> findMoviesNotDirectedByArtist(Long id) {
        return this.movieRepository.findMoviesNotDirectedByArtist(id);
    }

    public Iterable<Movie> findMoviesNotStarredByArtist(Long id) {
        return this.movieRepository.findMoviesNotStarredByArtist(id);
    }

    @Transactional
    public void removeActorFromMovie(Long movieId, Long actorId) {
        Movie movie = this.findMovie(movieId);
        Artist actor = this.artistService.find(actorId);
        movie.getActors().remove(actor);
        actor.getStarredMovies().remove(movie);
        this.saveMovie(movie);
        this.artistService.saveArtist(actor);
    }
    @Transactional
    public void updateTitle(String title, Long id) {
        Movie movie = this.findMovie(id);
        movie.setTitle(title);
        this.saveMovie(movie);
    }
    @Transactional
    public void addActorToMovie(Long movieId, Long actorId) {
        Movie movie = this.findMovie(movieId);
        Artist actor = this.artistService.find(actorId);
        actor.getStarredMovies().add(movie);
        movie.getActors().add(actor);
        this.saveMovie(movie);
        this.artistService.saveArtist(actor);
    }
    @Transactional
    public void removeDirector(Long movieId) {
        Movie movie = this.findMovie(movieId);
        Artist director = movie.getDirector();
        movie.setDirector(null);
        director.getDirectedMovies().remove(movie);
        this.saveMovie(movie);
        this.artistService.saveArtist(director);
    }
    @Transactional
    public void setDirectorToMovie(Long movieId, Long directorId) {
        Movie movie = this.findMovie(movieId);
        Artist director = this.artistService.find(directorId);
        movie.setDirector(director);
        director.getDirectedMovies().add(movie);
        this.saveMovie(movie);
        this.artistService.saveArtist(director);
    }

}
