package com.icrn.dao;

import java.util.List;
import java.util.Map;

import com.icrn.model.Movie;
import com.icrn.model.Staff;

public interface MovieDao {
	List<Movie> getMovies();
	List<Movie> getMoviesAlphabetically();
	List<Movie> getMoviesAlphabetically(int limit, int offset, boolean isDescending);
	List<Movie> searchMoviesByTitle(String title, int limit, int offset);
	Movie searchMovieById(int id);
	int numberOfCopiesAvailable(int filmid, int storeId);
	boolean rentMovie(int customerId, int movieId, int employeeId, int storeId);
	int returnMovie(int rentalId);
	long getMovieRowCount();
	List<Map<String,String>> getRentedMoviesByUser(int id);
	Map<Integer,String> getFilmCategories();
	long getMovieRowCountTitleSearch(String movieTitle);
	int payCustomerBalance(int customerId, int rentalId, int staffId, double amount);
	Map<Integer, List<Integer>> getOverdueRentals();
	List<Map<String, String>> getOverdueRentalsById(int customerId);
	Staff getStaffByUsername(String userName);
}
