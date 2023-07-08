package it.uniroma3.siw.controller;

import java.util.Set;

import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.ArtistService;
import it.uniroma3.siw.service.MovieService;
import it.uniroma3.siw.service.ReviewService;
import it.uniroma3.siw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.controller.validator.MovieValidator;
import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;
import jakarta.validation.Valid;

@Controller
public class MovieController {
	@Autowired
	private MovieService movieService;
	@Autowired
	private MovieValidator movieValidator;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private ReviewValidator reviewValidator;
	@Autowired
	private ArtistService artistService;
	@Autowired
	private UserService userService;
	@Autowired
	private SessionData sessionData;


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
			return "redirect:/admin/formUpdateMovie/" + movie.getId();
		} else {
			return "admin/formNewMovie";
		}
	}

	@GetMapping(value = "/admin/formUpdateMovie/{id}")
	public String formUpdateMovie(@PathVariable("id") Long id, Model model) {
		model.addAttribute("movie", this.movieService.findMovie(id));
		model.addAttribute("directorsList", this.artistService.findAllDirectorsNotInMovie(id));
		model.addAttribute("actorsList", this.artistService.findActorsNotInMovie(id));

		return "admin/formUpdateMovie";
	}

	@GetMapping(value = "/admin/manageMovies")
	public String manageMovies(Model model) {
		model.addAttribute("movies", this.movieService.getAllMovies());
		return "admin/manageMovies";
	}

	@GetMapping(value = "/admin/setDirectorToMovie/{directorId}/{movieId}")
	public String setDirectorToMovie(@PathVariable("directorId") Long directorId, @PathVariable("movieId") Long movieId) {
		this.movieService.setDirectorToMovie(movieId, directorId);
		return "redirect://admin/formUpdateMovie/" + movieId;
	}

	@GetMapping(value = "/admin/removeDirector/{id}")
	public String removeDirector(@PathVariable("id") Long id){
		this.movieService.removeDirector(id);
		return "redirect:/admin/formUpdateMovie/" + id;
	}

	@GetMapping("/movie/{id}")
	public String getMovie(@PathVariable("id") Long id, Model model) {
		model.addAttribute("movie", this.movieService.findMovie(id));
		model.addAttribute("averageRating", this.reviewService.getAverageRatingByMovie(id));
		model.addAttribute("review", new Review());
		model.addAttribute("reviewAuthorSet",  this.userService.getAllMovieReviewsAuthors(id));
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
	public String addActorToMovie(@PathVariable("actorId") Long actorId, @PathVariable("movieId") Long movieId) {
		this.movieService.addActorToMovie(movieId, actorId);
		return "redirect:/admin/formUpdateMovie/" + movieId;
	}

	@GetMapping(value = "/admin/removeActorFromMovie/{actorId}/{movieId}")
	public String removeActorFromMovie(@PathVariable("actorId") Long actorId, @PathVariable("movieId") Long movieId) {
		this.movieService.removeActorFromMovie(movieId, actorId);
		return "redirect:/admin/formUpdateMovie/" + movieId;
	}

	@PostMapping(value="/admin/updateTitle/{movieId}")
	public String updateTitle(@RequestParam("title") String title, @PathVariable("movieId") Long id){
		this.movieService.updateTitle(title, id);
		return "redirect:/admin/formUpdateMovie/" + id;
	}

	@PostMapping(value = "/movie/addNewReviewToMovie/{movieId}")
	public String newReview(@Valid @ModelAttribute("review") Review review, BindingResult bindingResult,
							@PathVariable("movieId") Long movieId, Model model){
		this.reviewValidator.validate(review, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.reviewService.saveReview(review, this.movieService.findMovie(movieId), this.sessionData.getLoggedUser());
			model.addAttribute("averageRating", this.reviewService.getAverageRatingByMovie(movieId));
		}
		return getMovie(movieId, model);
	}

	@GetMapping(value = "/admin/removeMovie/{movieId}")
	public String removeMovie(@PathVariable("movieId")Long id, Model model){
		this.movieService.deleteMovie(id);
		return "redirect:/admin/manageMovies";
	}
}


