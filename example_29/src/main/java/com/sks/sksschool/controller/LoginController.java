package com.sks.sksschool.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String displayLoginPage(@RequestParam(value = "error", required = false) String error,
                                   @RequestParam(value = "logout", required = false) String logout,
                                   @RequestParam(value = "register", required = false) String register,
                                   Model model) {
        String errorMessge = null;
        if (error != null) {
            errorMessge = "Username or Password is incorrect !!";
        }
        if (logout != null) {
            errorMessge = "You have been successfully logged out !!";
        }
        if (register != null) {
            errorMessge = "You are successfully registered. Please login with your credentials !!";
        }
        model.addAttribute("errorMessge", errorMessge);
        return "login.html";
    }

    /* As we have enabled csrf protecting using csrf token, see login.html page, by default logout
    * won't work and will throw 403 error. So we need to handle it manually by creating below.
    * Here in below first we are fetching the Authentication and then calling logout method of
    * SecurityContextLogoutHandler class. It'll invalidate http session and clear the authentication
    * and then we are redirecting to the login page with path param.*/

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if(authentication !=null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/login?logout=true";
    }
}