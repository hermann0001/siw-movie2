package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.session.SessionData;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {

    @Autowired
    private SessionData sessionData;

    @Autowired
    private CredentialsService credentialsService;

    @ModelAttribute("userDetails")
    public User getUser() {

        try{
            return this.sessionData.getLoggedUser();
        }
        catch (ClassCastException e){
            return null;
        }
    }

    @ModelAttribute("credentials")
    public Credentials getCredentials(){
        try{
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return credentialsService.getCredentials(userDetails.getUsername());
        }catch(ClassCastException e){
            return null;
        }
    }
}