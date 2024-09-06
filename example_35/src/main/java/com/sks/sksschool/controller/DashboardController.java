package com.sks.sksschool.controller;


import com.sks.sksschool.model.Person;
import com.sks.sksschool.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class DashboardController {

    @Autowired
    PersonRepository personRepository;

    /*This will get called if Successfully logged in. see MyProjectSecurityConfig class. Here in
    authentication object, we'll get the username, role etc, set in MyUsernamePasswordAuthentication \
    class*/

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication,
                                   HttpSession httpSession) {

        Person person=personRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("loggedInPerson",person);

        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        return "dashboard.html";
        //throw new RuntimeException("It's been a bad day");
    }

}
