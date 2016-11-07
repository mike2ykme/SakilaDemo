package com.icrn.controllers;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.icrn.model.Customer;
import com.icrn.service.CustomerService;

@Controller
public class HomeController {

	@Autowired
	CustomerService customerService;
	
	final static Logger logger = Logger.getLogger(HomeController.class);
	
	@RequestMapping(value="/")
	public String getHomepage(){
		return "home";
	}
	
	@RequestMapping(value="/404")
	public String get404(){
		return "404";
	}

//	@RequestMapping(value="/addCustomer", method=RequestMethod.GET)
//	public String addCustomer(Model model){
//		model.addAttribute("newCustomer",new Customer());
//		return "addCustomer";
//	}
//	
//	@RequestMapping(value="/verifyAddCustomer", method=RequestMethod.POST)
//	public String addCustomer(Model model, @Valid Customer newCustomer, Errors errors){
//		int id = 0;
//		
//		if(errors.hasErrors()){
//			model.addAttribute("newCustomer", newCustomer);
//			return "/addCustomer";
//		}
//		
//		newCustomer.setId(id);
//		
//		
//		
//		return "redirect:/selectCustomer?customerId=" + newCustomer.getId();
//	}
	
	@RequestMapping(value="/login")
	public String showLogin(Model model){
		
		return "login";
	}
}
