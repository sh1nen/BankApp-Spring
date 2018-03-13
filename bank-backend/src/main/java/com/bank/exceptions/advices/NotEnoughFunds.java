package com.bank.exceptions.advices;

import com.bank.exceptions.NotEnoughFundsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class NotEnoughFunds
{
    
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler(NotEnoughFundsException.class)
    public ModelAndView handleNotEnoughFunds(Exception exception)
    {
        log.error("Handling not enough funds exception");
        log.error(exception.getMessage());
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/412error");
        modelAndView.addObject("exception", exception);
        
        return modelAndView;
    }
}
