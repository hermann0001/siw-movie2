package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ArtistService{
    @Autowired
    ArtistRepository artistRepository;
    public Artist saveArtist(Artist artist) {
        return this.artistRepository.save(artist);
    }

    public Artist find(Long id) {
        return this.artistRepository.findById(id).orElse(null);
    }

    public Iterable<Artist> findAll() {
        return this.artistRepository.findAll();
    }

    public boolean exists(Artist artist) {
        return this.artistRepository.existsByNameAndSurname(artist.getName(), artist.getSurname());
    }

    public Iterable<Artist> findAllDirectorsNotInMovie(Long id) {
        return this.artistRepository.findAllDirectorsNotInMovie(id);
    }

    public Iterable<Artist> findActorsNotInMovie(Long id) {
        return this.artistRepository.findActorsNotInMovie(id);
    }
}
