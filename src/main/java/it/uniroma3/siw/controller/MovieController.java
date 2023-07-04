package it.uniroma3.siw.controller;

import java.util.Set;

import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.service.MovieService;
import it.uniroma3.siw.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.controller.validator.MovieValidator;
import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.repository.ArtistRepository;
import jakarta.validation.Valid;

@Controller
public class MovieController {
	@Autowired
	private MovieService movieService;
	@Autowired
	private ArtistRepository artistRepository;
	@Autowired
	private MovieValidator movieValidator;
	@Autowired
	private ReviewService reviewService;

	@GetMapping(value = "/admin/formNewMovie")
	public String formNewMovie(Model model) {
		model.addAttribute("movie", new Movie());
		return "admin/formNewMovie";
	}
	@PostMapping("/admin/movie")
	public String newMovie(@Valid @ModelAttribute("movie") Movie movie, BindingResult bindingResult, Model model) {
		this.movieValidator.validate(movie, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.movieService.saveMovie(movie);
			model.addAttribute("movie", movie);
			return "/movie/movie";
		} else {
			return "admin/formNewMovie";
		}
	}

	@GetMapping(value = "/admin/formUpdateMovie/{id}")
	public String formUpdateMovie(@PathVariable("id") Long id, Model model) {
		model.addAttribute("movie", this.movieService.findMovie(id));
		model.addAttribute("directorsList", this.artistRepository.findAllDirectorsNotInMovie(id));
		model.addAttribute("actorsList", this.artistRepository.findActorsNotInMovie(id));

		return "admin/formUpdateMovie";
	}

	@GetMapping(value = "/admin/manageMovies")
	public String manageMovies(Model model) {
		model.addAttribute("movies", this.movieService.getAllMovies());
		return "admin/manageMovies";
	}

	@GetMapping(value = "/admin/setDirectorToMovie/{directorId}/{movieId}")
	public String setDirectorToMovie(@PathVariable("directorId") Long directorId, @PathVariable("movieId") Long movieId, Model model) {
		Artist director = this.artistRepository.findById(directorId).get();
		Movie movie = this.movieService.findMovie(movieId);
		movie.setDirector(director);
		this.movieService.saveMovie(movie);

		return formUpdateMovie(movieId, model);
	}


	@GetMapping(value = "/admin/removeDirector/{id}")
	public String removeDirector(@PathVariable("id") Long id, Model model){
		Movie movie = this.movieService.findMovie(id);
		movie.setDirector(null);
		this.movieService.saveMovie(movie);

		return formUpdateMovie(id, model);
	}

	@GetMapping("/movie/{id}")
	public String getMovie(@PathVariable("id") Long id, Model model) {
		model.addAttribute("movie", this.movieService.findMovie(id));
		model.addAttribute("averageRating", this.reviewService.getAverageRatingByMovie(id));
		model.addAttribute("review", new Review());
		return "/movie/movie";
	}

	@GetMapping("/movie")
	public String getMovies(Model model) {
		model.addAttribute("movies", this.movieService.getAllMovies());
		return "movie/movies";
	}

	@GetMapping("/formSearchMovies")
	public String formSearchMovies() {
		return "/movie/formSearchMovies";
	}

	@PostMapping("/searchMovies")
	public String searchMovies(Model model, @RequestParam int year) {
		model.addAttribute("movies", this.movieService.findMovies(year));
		return "movie/foundMovies";
	}

	@GetMapping(value = "/admin/addActorToMovie/{actorId}/{movieId}")
	public String addActorToMovie(@PathVariable("actorId") Long actorId, @PathVariable("movieId") Long movieId, Model model) {
		Movie movie = this.movieService.findMovie(movieId);
		Artist actor = this.artistRepository.findById(actorId).get();
		Set<Artist> actors = movie.getActors();
		actors.add(actor);
		this.movieService.saveMovie(movie);

		return formUpdateMovie(movieId, model);
	}

	@GetMapping(value = "/admin/removeActorFromMovie/{actorId}/{movieId}")
	public String removeActorFromMovie(@PathVariable("actorId") Long actorId, @PathVariable("movieId") Long movieId, Model model) {
		Movie movie = this.movieService.findMovie(movieId);
		Artist actor = this.artistRepository.findById(actorId).get();
		Set<Artist> actors = movie.getActors();
		actors.remove(actor);
		this.movieService.saveMovie(movie);

		return formUpdateMovie(movieId, model);
	}

	@PostMapping(value="/admin/updateTitle/{movieId}")
	public String updateTitle(@RequestParam("title") String title, @PathVariable("movieId") Long id, Model model){
		Movie movie = this.movieService.findMovie(id);
		movie.setTitle(title);
		this.movieService.saveMovie(movie);

		return formUpdateMovie(id, model);
	}
}


