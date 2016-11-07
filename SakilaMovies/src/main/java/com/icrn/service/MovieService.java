package com.icrn.service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.icrn.dao.MovieDao;
import com.icrn.model.Movie;
import com.icrn.model.Staff;

@Service
public class MovieService{
	final static Logger logger = Logger.getLogger(MovieService.class);

	@Autowired
	private MovieDao movieDao;
	public List<Movie> getMovies() {
		return movieDao.getMovies();
	}

	public List<Movie> getMoviesAlphabetically() {
		logger.info("getMoviesAlphabetically called");
		return movieDao.getMoviesAlphabetically();
	}

	public List<Movie> getMoviesAlphabetically(int limit, int offset, boolean isDescending) {
		logger.info("getMoviesAlphabetically called");
		return movieDao.getMoviesAlphabetically(limit, offset, isDescending);
	}

	public List<Movie> searchMoviesByTitle(String title, int limit, int offset) {
		logger.info("searchMoviesByTitle called");
		return movieDao.searchMoviesByTitle(title, limit, offset);
	}

	public Movie searchMovieById(int id) {
		logger.info("searchMovieById called");
		Movie movie = movieDao.searchMovieById(id);
		return movie;
	}

	public int numberOfCopiesAvailable(int filmId) {
		logger.info("isMovieAvailable called");
		Principal principal = SecurityContextHolder.getContext().getAuthentication();
		String username = principal.getName();
		logger.info(username+ "has called numberOfCopiesAvailable");
		return movieDao.numberOfCopiesAvailable(filmId, getStaffByUsername(username).getStoreId());
	}
	
	public Staff getStaffByUsername(String userName){
		logger.info("getStaffByUsername called");
		return movieDao.getStaffByUsername(userName); 
	}

	public boolean rentMovie(int userId, int movieId) {
		logger.info("rentMovie called");
		Principal principal = SecurityContextHolder.getContext().getAuthentication();
		String username = principal.getName();
		logger.info(username+ "has called rentMovie");
		Staff staff = getStaffByUsername(username);
		return movieDao.rentMovie(userId, movieId, staff.getStaffId(),staff.getStoreId());
		
	}

	public long getNumberOfMovies() {
		logger.info("getNumberOfMovies called");
		return movieDao.getMovieRowCount();
	}

	public List<Map<String, String>> getRentedMoviesByUser(int id) {
		logger.info("getRendtedMoviesByUser called");
		return movieDao.getRentedMoviesByUser(id);
	}
	
	public double getPageCount(int perPageValue){
		logger.info("getPageCount called");
		return Math.ceil((double)movieDao.getMovieRowCount()/perPageValue);
	}
	public Map<Integer,String> getFilmCategories(){
		logger.info("getFilmCategories called");
		return movieDao.getFilmCategories();
	}

	public double getPageCountTitleSearch(int perPage, String movieTitle) {
		logger.info("getPageCountTitleSearch called");
		double value = Math.ceil((double)movieDao.getMovieRowCountTitleSearch(movieTitle)/perPage);
		
		logger.info(value);
		return value;
	}
	
	public boolean returnMovie(int rentalId){
		logger.info("returnMovie called");
		return movieDao.returnMovie(rentalId) == 1;
	}
	
	public boolean payBalance(int customerId,int rentalId, double amount){
		logger.info("payBalance has been called");
		int staffId = 1;
		return movieDao.payCustomerBalance(customerId,rentalId,staffId,amount) ==1;
	}
	
	public List<Map<String,String>> getOverdueMoviesByUser(int customerId){
		logger.info("getOverdueMoviesByUser has been called");
		List<Map<String,String>> movieMap= movieDao.getOverdueRentalsById(customerId);
		logger.info(movieMap);
		return movieMap;
		
	}

}