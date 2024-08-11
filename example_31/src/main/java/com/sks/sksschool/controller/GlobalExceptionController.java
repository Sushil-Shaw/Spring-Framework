package com.sks.sksschool.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.MalformedParameterizedTypeException;

/* This is created to handle the error page. If we don't create this then we'll see the error logs in
* the browser. So, here using two annotation one is @ControllerAdvice which advices all the Controller
* that if any exceptions occurs and you do not have any exception logic then send this to me.
* And using @ExceptionHandler we are defining which all Exceptions this method will handle.
* We can create different methods for different exceptions such as NullPointer etc.
*
* Here ModelAndView means we can create Model and View also with together. */

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(Exception.class)
    public ModelAndView genericException(Exception exception){

        ModelAndView errorPage = new ModelAndView();
        errorPage.setViewName("error");//means view is error.html
        errorPage.addObject("errorMsg",exception.getMessage());//this is model attribute
        return errorPage;

    }
}
