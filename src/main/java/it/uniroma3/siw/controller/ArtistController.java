package it.uniroma3.siw.controller;

import it.uniroma3.siw.service.ArtistService;
import it.uniroma3.siw.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Artist;

@Controller
public class ArtistController {
	 @Autowired
	private ArtistService artistService;
	 @Autowired
	private MovieService movieService;

	@GetMapping(value="/admin/formNewArtist")
	public String formNewArtist(Model model) {
		model.addAttribute("artist", new Artist());
		return "admin/formNewArtist";
	}

	//TODO: aggiungere l'artist validator
	@PostMapping("/admin/artist")
	public String newArtist(@ModelAttribute("artist") Artist artist, Model model) {
		if (!artistService.exists(artist)) {
			this.artistService.saveArtist(artist);
			model.addAttribute("artist", artist);
			return "artist/artist";
		} else {
			model.addAttribute("messaggioErrore", "Questo artista esiste già");
			return "admin/formNewArtist";
		}
	}

	@GetMapping("/artist/{id}")
	public String getArtist(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artist", this.artistService.find(id));
		return "artist/artist";
	}

	@GetMapping("/artist")
	public String getArtists(Model model) {
		model.addAttribute("artists", this.artistService.findAll());
		return "artist/artists";
	}

	@GetMapping(value = "/admin/manageArtists")
	public String manageMovies(Model model) {
		model.addAttribute("artists", this.artistService.findAll());
		return "admin/manageArtists";
	}

	@GetMapping(value = "/admin/formUpdateArtist/{id}")
	public String formUpdateMovie(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artist", this.artistService.find(id));
		model.addAttribute("directedMoviesList", this.movieService.findMoviesNotDirectedByArtist(id));
		model.addAttribute("starredMoviesList", this.movieService.findMoviesNotStarredByArtist(id));

		return "admin/formUpdateArtist";
	}

	@GetMapping(value = "/admin/removeMovieFromStarredMovies/{actorId}/{movieId}")
	public String removeMovieFromStarredMovies(@PathVariable("actorId") Long idA, @PathVariable("movieId") Long idM){
		this.movieService.removeActorFromMovie(idM, idA);
		return "redirect:/admin/formUpdateArtist/" + idA;
	}

	@GetMapping(value = "/admin/removeMovieFromDirectedMovies/{actorId}/{movieId}")
	public String removeMovieFromDirectedMovies(@PathVariable("actorId") Long idA, @PathVariable("movieId") Long idM){
		this.movieService.removeDirector(idM);
		return "redirect:/admin/formUpdateArtist/" + idA;
	}

	@GetMapping(value = "/admin/addMovieToStarredMovies/{actorId}/{movieId}")
	public String addMovieToStarredMovies(@PathVariable("actorId") Long idA, @PathVariable("movieId") Long idM) {
		this.movieService.addActorToMovie(idM, idA);
		return "redirect:/admin/formUpdateArtist/" + idA;
	}

	@GetMapping(value = "/admin/addMovieToDirectedMovies/{actorId}/{movieId}")
	public String addMovieToDirectedMovies(@PathVariable("actorId") Long idA, @PathVariable("movieId") Long idM) {
		this.movieService.setDirectorToMovie(idM, idA);
		return "redirect:/admin/formUpdateArtist/" + idA;
	}
}
