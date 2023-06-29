package it.uniroma3.siw.controller;

import it.uniroma3.siw.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.repository.ArtistRepository;

@Controller
public class ArtistController {
	
	 @Autowired
	private ArtistRepository artistRepository;
	 @Autowired
	private MovieRepository movieRepository;

	@GetMapping(value="/admin/formNewArtist")
	public String formNewArtist(Model model) {
		model.addAttribute("artist", new Artist());
		return "admin/formNewArtist";
	}

	//TODO: aggiungere l'artist validator
	@PostMapping("/admin/artist")
	public String newArtist(@ModelAttribute("artist") Artist artist, Model model) {
		if (!artistRepository.existsByNameAndSurname(artist.getName(), artist.getSurname())) {
			this.artistRepository.save(artist); 
			model.addAttribute("artist", artist);
			return "artist/artist";
		} else {
			model.addAttribute("messaggioErrore", "Questo artista esiste già");
			return "admin/formNewArtist";
		}
	}

	@GetMapping("/artist/{id}")
	public String getArtist(@PathVariable("id") Long id, Model model) {
		Artist artist = this.artistRepository.findById(id).get();
		System.out.println(artist.getDirectedMovies().toString());
		model.addAttribute("artist", artist);
		return "artist/artist";
	}

	@GetMapping("/artist")
	public String getArtists(Model model) {
		model.addAttribute("artists", this.artistRepository.findAll());
		return "artist/artists";
	}

	@GetMapping(value = "/admin/manageArtists")
	public String manageMovies(Model model) {
		model.addAttribute("artists", this.artistRepository.findAll());
		return "admin/manageArtists";
	}

	@GetMapping(value = "/admin/formUpdateArtist/{id}")
	public String formUpdateMovie(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artist", this.artistRepository.findById(id).get());
		model.addAttribute("directedMoviesList", this.movieRepository.findMoviesNotDirectedByArtist(id));
		model.addAttribute("starredMoviesList", this.movieRepository.findMoviesNotStarredByArtist(id));

		return "admin/formUpdateArtist";
	}

	@GetMapping(value = "/admin/removeMovieFromStarredMovies/{actorId}/{movieId}")
	public String removeMovieFromStarredMovies(@PathVariable("actorId") Long idA, @PathVariable("movieId") Long idM, Model model){
		//TODO: FAI LO STESSO DI removeActorFromMovie() :: MovieController
		return "foo";
	}

	@GetMapping(value = "/admin/removeMovieFromDirectedMovies/{actorId}/{movieId}")
	public String removeMovieFromDirectedMovies(@PathVariable("actorId") Long idA, @PathVariable("movieId") Long idM, Model model){
		//TODO: FAI LO STESSO DI removeDirectorFromMovie() :: MovieController
		return "foo";
	}

	@GetMapping(value = "/admin/addMovieToStarredMovies/{actorId}/{movieId}")
	public String addMovieToStarredMovies(@PathVariable("actorId") Long idA, @PathVariable("movieId") Long idM, Model model) {
		//TODO: FAI LO STESSO DI addActorToMovie() :: MovieController
		return "foo";
	}

	@GetMapping(value = "/admin/addMovieToDirectedMovies/{actorId}/{movieId}")
	public String addMovieToDirectedMovies(@PathVariable("actorId") Long idA, @PathVariable("movieId") Long idM, Model model) {
		//TODO: FAI LO STESSO DI addDirectorToMovie() :: MovieController
		return "foo";
	}
}
