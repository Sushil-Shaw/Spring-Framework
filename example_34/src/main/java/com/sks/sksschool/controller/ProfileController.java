package com.sks.sksschool.controller;

import com.sks.sksschool.model.Address;
import com.sks.sksschool.model.Person;
import com.sks.sksschool.model.Profile;
import com.sks.sksschool.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {

    @Autowired
    PersonRepository personRepository;

    @GetMapping("/displayProfile")
    public ModelAndView displayProfile(Model model, HttpSession httpSession){
        ModelAndView modelAndView=new ModelAndView("profile.html");
        Profile profile=new Profile();

        Person person= (Person) httpSession.getAttribute("loggedInPerson");
        profile.setName(person.getName());
        profile.setMobileNumber(person.getMobileNum());
        profile.setEmail(person.getEmail());

        if(null !=person.getAddress()){
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setAddress2(person.getAddress().getAddress2());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZipCode());
        }

        modelAndView.addObject("profile",profile);
        return modelAndView;
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@Valid @ModelAttribute("profile") Profile profile,
                                Errors errors, HttpSession httpSession, Authentication authentication){

        if(errors.hasErrors()){
            return "profile.html";
        }

        Person person = (Person) httpSession.getAttribute("loggedInPerson");
        person.setName(profile.getName());
        person.setEmail(profile.getEmail());
        person.setMobileNum(profile.getMobileNumber());

        Address address = person.getAddress();
        if(address ==null) address = new Address();


        address.setAddress1(profile.getAddress1());
        address.setAddress2(profile.getAddress2());
        address.setCity(profile.getCity());
        address.setState(profile.getState());
        address.setZipCode(profile.getZipCode());
        person.setAddress(address);

        personRepository.save(person);

        httpSession.setAttribute("loggedInPerson",person);

        return "redirect:/displayProfile";

    }
}
