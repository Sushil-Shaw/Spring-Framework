package com.sks.sksschool.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 This is a configuration class where we defined some of the url path, if the path /courses then
 go to courses.html
 We can do this by using Controller class as well like we did for HomeController. But as we are currently
 not doing anything in the Controller, just returning the home.html class.
 So in this way also, we can do the same by not creating any Controller class.
 */

@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/courses").setViewName("courses");
        registry.addViewController("/about").setViewName("about");
       // registry.addViewController("/contact").setViewName("contact");
    }
}

