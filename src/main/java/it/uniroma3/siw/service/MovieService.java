package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Transactional
    public Movie getMovie(Long id){
        return this.movieRepository.findById(id).get();
    }
}
