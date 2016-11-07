package com.icrn.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.icrn.model.Movie;
import com.icrn.service.CustomerService;
import com.icrn.service.MovieService;

@Controller
public class MovieController {
	
	final static Logger logger = Logger.getLogger(MovieController.class);
	
	@Autowired
	private MovieService movieService;
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="/selectMovie")
	public String selectCustomer(Model model, 
					@RequestParam(value="customerId", defaultValue="0") int customerId){
//		Customer customer = customerService.searchCustomerById(customerId);
//		model.addAttribute("customer",customer);
//		double balance = customerService.getCustomerBalance(customerId);
//		model.addAttribute("balance",balance);
		return "searchMovie";
	}
	
	public int getOffset(int page, int perPage){
		
		return ((page-1)*perPage);
	}
	
	@RequestMapping(value="/movieSearch")
	public String showMovieSearch(Model model, @RequestParam(value="perPage", defaultValue="25") int perPage,
										@RequestParam(value="pageId", defaultValue="1")int pageId,
										@RequestParam(value="sortOrder", defaultValue="ASC")String sortOrder,
										@RequestParam(value="error", defaultValue="false")String error,
										@RequestParam(value="customerId", defaultValue="0")int customerId){
		logger.info("inside of /movieSearch mapping");

//		boolean isDescending = !sortOrder.equals("ASC");
//		boolean hasError = ((error.matches("true")));
		
		if(customerId <= 0 || customerService.getDisabledCustomerIdList().contains(customerId)){
			logger.info("customerId is <=0 or inactive");
			return "redirect:/customerSearch";
		}
		if(error.matches("true")){
				return "redirect:/movieSearch?customerId="+customerId;
		}

		
		double pages = movieService.getPageCount(perPage);
		if(pages ==0.0) pages=1.0;
		
		if(pageId-1>=pages || pageId <=0 ||perPage >50 || perPage<5){
			logger.info("validation error on passed parameter");
			return "redirect:/movieSearch?customerId="+customerId;
		}
		
//		List<Movie> movieList = movieService.getMoviesAlphabetically(perPage, getOffset(pageId,perPage), isDescending);

		model.addAttribute("customerId",customerId);
		model.addAttribute("movieCategories",movieService.getFilmCategories());
		model.addAttribute("movieList", movieService.getMoviesAlphabetically(perPage, getOffset(pageId,perPage), !sortOrder.equals("ASC")));
		model.addAttribute("currentPage",pageId);
		model.addAttribute("maxPage", ((int)pages));
		model.addAttribute("perPage",perPage);
		model.addAttribute("sortOrder",sortOrder);
		model.addAttribute("pagesURL","/movieSearch");

		return "searchMovie";
		
	}
	
	@RequestMapping(value="/movieSearch/byTitle")
	public String searchMovieByTitle(Model model, @RequestParam(value="movieTitle",defaultValue="")String movieTitle,
													@RequestParam(value="perPage", defaultValue="25") int perPage,
													@RequestParam(value="pageId", defaultValue="1")int pageId,
													//@RequestParam(value="sortOrder", defaultValue="ASC")String sortOrder,
													@RequestParam(value="customerId",defaultValue="0")int customerId){
		logger.info("/movieSearch/byTitle is called");

		if(customerId <= 0 || customerService.getDisabledCustomerIdList().contains(customerId)){
			logger.info("customerId is <=0 or inactive");
			return "redirect:/customerSearch";
		}
		model.addAttribute("customerId",customerId);
		model.addAttribute("movieList", movieService.searchMoviesByTitle(movieTitle, perPage , getOffset(pageId,perPage)));
		model.addAttribute("currentPage",pageId);
		model.addAttribute("maxPage",movieService.getPageCountTitleSearch(perPage,movieTitle));
		model.addAttribute("perPage",perPage);
		model.addAttribute("movieTitle",movieTitle);
		model.addAttribute("pagesURL","/movieSearch/byTitle");
		
		
		return "searchMovie";
		
	}

	@RequestMapping(value="/selectMovie/byId")
	public String selectMovieById(Model model, @RequestParam(value="movieId", defaultValue="0") int movieId,
													@RequestParam(value="customerId", defaultValue="0")int customerId){
		logger.info("inside of /movieSearch mapping");
		if(customerId <= 0 || customerService.getDisabledCustomerIdList().contains(customerId)){
			logger.info("customerId is <=0 or inactive");
			return "redirect:/customerSearch";
		}
		model.addAttribute("customer",customerService.searchCustomerById(customerId));
		model.addAttribute("movie",movieService.searchMovieById(movieId));
		model.addAttribute("copiesAvailable",movieService.numberOfCopiesAvailable(movieId));
		
		return "showMovie";
	}
	
	
	@RequestMapping(value="/returnMovie")
	public String returnMovie(Model model,@RequestParam(value="rentalId", defaultValue="0") int rentalId,
											@RequestParam(value="customerId", defaultValue="0")int customerId){
		logger.info("/returnMovie mapping called");
		if(customerId <= 0 || customerService.getDisabledCustomerIdList().contains(customerId) || rentalId == 0){
			logger.info("customerId is <=0 or inactive || rentalId = 0");
			return "redirect:/customerSearch";
		}

		logger.info("rentalId: " + rentalId 
					+ "\ncustomerId: "+ customerId );
		
		model.addAttribute("customerId",customerId);
		model.addAttribute("rentalId",rentalId);
		model.addAttribute("customerBalance",customerService.getCustomerBalance(customerId));
		
		
		
		if(movieService.returnMovie(rentalId)){
			model.addAttribute("customerBalance",customerService.getCustomerBalance(customerId));
			return "movieReturnedSuccess";
			
		}
		return "movieReturnedFailure";
	}
	
	@RequestMapping(value="/payBalance")
	public String payBalance(Model model, @RequestParam(value="customerId", defaultValue="0")int customerId,
											@RequestParam(value="rentalId", defaultValue="0")int rentalId,
											@RequestParam(value="customerBalance", defaultValue="0")double customerBalance){
		if(customerId <= 0 || customerService.getDisabledCustomerIdList().contains(customerId) || rentalId == 0 || customerBalance == 0){
			logger.info("customerId is <=0 or inactive || rentalId = 0 || customerBalance =0");
			logger.warn("customerId: "+customerId
					+"\nrentalId: "+rentalId
					+"\ncustomerBalancePaid: "+customerBalance);
			return "redirect:balancePaidFailure";
		}
		if(customerBalance < customerService.getCustomerBalance(customerId)){
			logger.warn("balanance paid less than balance owed");
			return "balancePaidFailure";
		}
		if(movieService.payBalance(customerId,rentalId,customerBalance)){
			model.addAttribute("customerId",customerId);
			model.addAttribute("rentalId",rentalId);
			model.addAttribute("customerBalance",0.0);
			return "balancePaidSuccess";
		}
		logger.error("Failure to pay balance"
				+ "\ncustomer = "+ customerId
				+ "\nrentalId = "+ rentalId);
		model.addAttribute("customerId",customerId);
		model.addAttribute("rentalId",rentalId);
		return "balancePaidFailure";
	}
	
	@RequestMapping(value="/confirmMovieRental")
	public String confirmMovieRental(Model model, @RequestParam(value="movieId", defaultValue="0") int movieId,
													@RequestParam(value="customerId", defaultValue="0")int customerId){
		logger.info("inside of /confirmMovieRental mapping");
		if(customerId <= 0 || customerService.getDisabledCustomerIdList().contains(customerId)){
			logger.info("customerId is <=0 or inactive");
			return "redirect:/customerSearch";
		}
		
		int employeeId = 1;
		logger.info("TODO: Implement confirmMovieRental method to verify and call DB");
		if(movieService.rentMovie(customerId, movieId)){
			model.addAttribute("customerId",customerId);
			model.addAttribute("movieId",movieId);
			logger.info("Success of rent movie"
					+ "\ncustomer = "+ customerId
					+ "\nmovieId = "+ movieId);
			return "rentalSuccess";
		}
		logger.error("Failure to rent movie"
				+ "\ncustomer = "+ customerId
				+ "\nmovieId = "+ movieId);
		return "rentalFailure";
	}
}
//
//
//@RequestMapping(value="/customerSearch",method=RequestMethod.GET)
//public String showUserSearch(Model model, @RequestParam(value="perPage", defaultValue="25") int perPage,
//									@RequestParam(value="pageId", defaultValue="1")int pageId,
//									@RequestParam(value="sortOrder", defaultValue="ASC")String sortOrder,
//									@RequestParam(value="error", defaultValue="false")String error){
//	boolean isDescending = sortOrder.matches("DESC");
//	boolean hasError = (!(error.matches("false")));
//	logger.info("inside Customer Search mapping");
//	double pages = customerService.getPageCount(perPage);
//	logger.info(perPage);
//	if(pages ==0.0) pages=1.0;
//	if(pageId-1>=pages || pageId <=0 ||perPage >50 || perPage<5){
//		return "redirect:/customerSearch?error=true";
//	}
//	List<Customer> customerList = customerService.getCustomersAlphabetically(perPage, getOffset(pageId,perPage), isDescending);
//	logger.info(customerList);
//	if(hasError){
//		model.addAttribute("outsideRangeMessage","Max value for perPage is 50, minimum value is 5");
//	}
//	model.addAttribute("customers", customerList);
//	model.addAttribute("currentPage",pageId);
//	model.addAttribute("maxPage", ((int)pages));
//	model.addAttribute("perPage",perPage);
//	model.addAttribute("sortOrder",sortOrder);
//	
//	logger.info("pages: "+pages+"\ncurrentPage:"+pageId);
//	return "searchCustomer";
//}