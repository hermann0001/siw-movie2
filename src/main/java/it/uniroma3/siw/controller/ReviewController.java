package it.uniroma3.siw.controller;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReviewController {

    @PostMapping(value = "addNewReviewToMovie/{movieId}/{userId}")
    public String addNewReviewToMovie(@PathVariable("movieId") Long movieId, @PathVariable("userId") Long userId, Model model){
        return "TODO";
    }
}
