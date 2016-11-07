package com.icrn.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionHandlers {
	
	final static Logger logger = Logger.getLogger(ExceptionHandlers.class);


	@ExceptionHandler(Exception.class)
	public String handleExceptions(Exception ex){
//		return new ModelAndView("error");
		logger.error(ex.getMessage());
		
		return "error";
	}
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleError404(NoHandlerFoundException ex)   {
    	logger.error(ex.getMessage());
    	logger.error(ex.getCause());
    	return "404";
    }
    
    
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public ModelAndView handleError404(NoHandlerFoundException ex)   {
//            ModelAndView mav = new ModelAndView("error");
//            mav.addObject("e", e);  
//            //mav.addObject("errorcode", "404");
//            return mav;
//    }
}
