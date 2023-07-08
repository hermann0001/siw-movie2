package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class ArtistService{
    @Autowired
    ArtistRepository artistRepository;

    @Transactional
    public Artist saveArtist(Artist artist) {
        return this.artistRepository.save(artist);
    }

    public Artist findArtist(Long id) {
        return this.artistRepository.findById(id).orElse(null);
    }

    public Iterable<Artist> findAll() {
        return this.artistRepository.findAll();
    }

    @Transactional
    public boolean exists(Artist artist) {
        return this.artistRepository.existsByNameAndSurname(artist.getName(), artist.getSurname());
    }

    public Iterable<Artist> findAllDirectorsNotInMovie(Long id) {
        return this.artistRepository.findAllDirectorsNotInMovie(id);
    }

    public Iterable<Artist> findActorsNotInMovie(Long id) {
        return this.artistRepository.findActorsNotInMovie(id);
    }

    @Transactional
    public void deleteArtist(Long id) {
        Artist artist = this.findArtist(id);

        for(Movie m : artist.getDirectedMovies())       //cancello associazione regista
            m.setDirector(null);

        for(Movie m : artist.getStarredMovies())       //cancello associazione attore
            m.getActors().remove(artist);

        this.artistRepository.delete(artist);
    }
}
