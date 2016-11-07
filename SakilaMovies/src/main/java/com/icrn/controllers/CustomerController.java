package com.icrn.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.icrn.service.MovieService;
@Controller
public class CustomerController {

	@Autowired
	CustomerService customerService;
	@Autowired
	MovieService movieService;
	
	
	final static Logger logger = Logger.getLogger(CustomerController.class);

	
	public int getOffset(int page, int perPage){
		
		return ((page-1)*perPage);
	}
	@RequestMapping(value="/customerSearch",method=RequestMethod.GET)
	public String showUserSearch(Model model, @RequestParam(value="perPage", defaultValue="25") int perPage,
										@RequestParam(value="pageId", defaultValue="1")int pageId,
										@RequestParam(value="sortOrder", defaultValue="ASC")String sortOrder,
										@RequestParam(value="error", defaultValue="false")String error){
		boolean isDescending = sortOrder.equals("DESC");
		boolean hasError = (!(error.matches("false")));
		logger.info("inside Customer Search mapping");
		double pages = customerService.getPageCount(perPage);
		logger.info(perPage);
		if(pages ==0.0) pages=1.0;
		if(pageId-1>=pages || pageId <=0 ||perPage >50 || perPage<5){
			return "redirect:/customerSearch?error=true";
		}
		List<Customer> customerList = customerService.getCustomersAlphabetically(perPage, getOffset(pageId,perPage), isDescending);
		if(hasError){
			model.addAttribute("outsideRangeMessage","Max value for perPage is 50, minimum value is 5");
		}
		model.addAttribute("customers", customerList);
		model.addAttribute("currentPage",pageId);
		model.addAttribute("maxPage", ((int)pages));
		model.addAttribute("perPage",perPage);
		model.addAttribute("sortOrder",sortOrder);
		
		logger.info("pages: "+pages+"\ncurrentPage:"+pageId);
		return "searchCustomer";
	}
	@RequestMapping(value="/customerSearch/lastName",method=RequestMethod.GET)
	public String searchForUserByLastName(Model model, @RequestParam(value="customerName",defaultValue="a")String customerName){
		logger.info("inside Customer Search mapping");
		logger.info("value of customerName: " + customerName.toString());
		
		List<Customer> customerList = customerService.searchCustomersByLastName(customerName);
		
		model.addAttribute("customers", customerList);
		model.addAttribute("lSearch",customerName);
		
		return "searchCustomer";
	}
	@RequestMapping(value="/customerSearch/firstName",method=RequestMethod.GET)
	public String searchForUserByFirstName(Model model, @RequestParam(value="customerName",defaultValue="a")String customerName){
		logger.info("inside Customer Search mapping");
		logger.info("value of customerName: " + customerName.toString());
		
		List<Customer> customerList = customerService.searchCustomersByFirstName(customerName);
		
		model.addAttribute("customers", customerList);
		model.addAttribute("fSearch",customerName);
		
		return "searchCustomer";
	}
	@RequestMapping(value="/selectCustomer")
	public String selectCustomer(Model model, 
					@RequestParam(value="customerId", defaultValue="0") int customerId){

		if(customerId <= 0 || customerService.getDisabledCustomerIdList().contains(customerId)){
			logger.info("customerId is <=0 or inactive");
			return "redirect:/customerSearch?error=true";
		}
		Customer customer = customerService.searchCustomerById(customerId);
		double balance = customerService.getCustomerBalance(customerId);
		
		model.addAttribute("customer",customer);
		model.addAttribute("balance",balance);
		model.addAttribute("rentedMovies",movieService.getRentedMoviesByUser(customerId));
		model.addAttribute("overdueMovies",movieService.getOverdueMoviesByUser(customerId));
		return "showCustomer";
	}
	@RequestMapping(value="/updateCustomer", method=RequestMethod.POST)
	public String updateCustomer(Model model,
			@RequestParam(value="customerId", defaultValue="0") int customerId){
		if(customerId == 0){
			return "redirect:/searchCustomer";
		}
		
		return "updateCustomer";
	}
	@RequestMapping(value="/validateCustomerUpdate",method=RequestMethod.POST)
	public String validateCustomerUpdate(Model model,
				@Valid Customer customer, Errors errors){
		if(errors.hasErrors()){
			return "updateCustomer";
		}
		
		return "redirect:/selectCustomer?customerId=" + customer.getId();
	}
	@RequestMapping(value="/rentMovie", method=RequestMethod.POST)
	public String selectMovie(Model model, @RequestParam(value="id", defaultValue="0") int customerId){
		if(customerId <= 0 || customerService.getDisabledCustomerIdList().contains(customerId)){
			logger.info("customerId is <=0 or inactive");
			return "redirect:/customerSearch";
		}
		
		
		
		return "searchMovies";
	}
	
}
