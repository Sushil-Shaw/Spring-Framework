package com.sks.sksschool.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class DashboardController {

    /*This will get called if Successfully logged in. see MyProjectSecurityConfig class. Here in
    authentication object, we'll get the username, role etc.*/
    @RequestMapping("/dashboard")
    public String displayDashboard(Model model,Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        return "dashboard.html";
        //throw new RuntimeException("It's been a bad day");
    }

}
