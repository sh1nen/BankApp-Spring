package com.bank.exceptions.advices;

import com.bank.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class UserNotFound
{
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handleUserNotFound(Exception exception)
    {
        log.error("Handling user not found exception");
        log.error(exception.getMessage());
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/404error");
        modelAndView.addObject("exception", exception);
        
        return modelAndView;
    }
}