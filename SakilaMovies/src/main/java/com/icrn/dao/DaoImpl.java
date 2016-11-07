package com.icrn.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.icrn.model.Customer;
import com.icrn.model.Movie;
import com.icrn.model.Staff;

@Repository("DaoImpl")
public class DaoImpl implements CustomerDao, MovieDao, StaffDao {


	final static Logger logger = Logger.getLogger(DaoImpl.class);

	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	@Autowired
	public void setJdbcTemplate(DataSource ds) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(ds);
	}
/*
 * ************************************************************************************************************************************
 * 
 * 
 * 
 *  ========================= Look at those Customers ===================================================
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * ************************************************************************************************************************************
 */
	@Override
	public List<Customer> getCustomers() {
		return jdbcTemplate.query("SELECT * FROM customer", this::mapSearchCustomerRow);

	}

	@Override
	public List<Customer> getCustomersAlphabetically() {
		return jdbcTemplate.query("SELECT * FROM customer ORDER BY last_name DESC", this::mapSearchCustomerRow);
	}
	public Customer mapSearchCustomerRow(ResultSet rs, int rowNumber) throws SQLException {
		logger.info("maprow is called");
		Customer customer = new Customer(rs.getInt("customer_id"), rs.getString("first_name"),
				rs.getString("last_name"));
		customer.setStatus(rs.getBoolean("active"));
		return customer;

	}
	public Customer mapFullCustomerRow(ResultSet rs, int rowNumber) throws SQLException {
		logger.info("maprow is called");
		Customer customer = new Customer(rs.getInt("customer_id"), rs.getString("first_name"),
				rs.getString("last_name"), rs.getString("email"),rs.getString("address"), rs.getString("city"), rs.getInt("postal_code"), rs.getBoolean("active"));
		return customer;

	}
	@Override
	public List<Customer> getCustomersAlphabetically(int limit, int offset, boolean isDescending) {
		logger.info("getCustomersAlphabetically is called");
		String sql; // = "SELECT * FROM sakila.customer order by first_name ASC
					// LIMIT 10 OFFSET 20";
		if (isDescending) {
			sql = "SELECT * FROM sakila.customer order by last_name DESC LIMIT :LIMIT OFFSET :OFFSET";
		} else {
			sql = "SELECT * FROM sakila.customer order by last_name ASC LIMIT :LIMIT OFFSET :OFFSET";
		}
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("LIMIT", limit);
		params.addValue("OFFSET", offset);
		return jdbcTemplate.query(sql, params, this::mapSearchCustomerRow);
	}
	@Override
	public List<Customer> searchCustomersByFirstName(String firstName, int limit) {
		logger.info("searchCustomersByFirstName is called");
		String sql = "SELECT * FROM SAKILA.CUSTOMER WHERE first_name LIKE :NAME LIMIT :LIMIT";
		MapSqlParameterSource params = new MapSqlParameterSource();
		firstName = firstName + "%";
		params.addValue("NAME", firstName);
		params.addValue("LIMIT", limit);
		return jdbcTemplate.query(sql, params, this::mapSearchCustomerRow);
	}
	@Override
	public List<Customer> searchCustomersByLastName(String lastName, int limit) {
		logger.info("searchCustomersByLastName is called");
		String sql = "SELECT * FROM sakila.customer WHERE last_name LIKE :NAME LIMIT :LIMIT";
		lastName = lastName + "%";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("NAME", lastName);
		params.addValue("LIMIT", limit);
		return jdbcTemplate.query(sql, params, this::mapSearchCustomerRow);
	}
	@Override
	public Customer searchCustomerById(int id) {
		logger.info("Search customer by id function");
		String sql = 
				"SELECT customer_id, first_name, last_name, email, address, city, postal_code, active "
				+ "FROM sakila.customer "
				+ "INNER JOIN address "
				+ "ON customer.address_id = address.address_id "
				+ "INNER JOIN city "
				+ "ON address.city_id = city.city_id "
				+ "WHERE customer_id =:id";

		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("id", id);
		return jdbcTemplate.queryForObject(sql, params, this::mapFullCustomerRow);
	}
	@Override
	public boolean updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean deleteCustomer(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public long getRowCount() {
		return jdbcTemplate.getJdbcOperations().queryForObject("SELECT COUNT(*) FROM customer", (rs, rowNum) -> {
			return new Long(rs.getLong(1));
		});
	}
	@Override
	public double getBalance(int id) {
//		 SqlParameterSource namedParameters = new MapSqlParameterSource("balance", id);
		List<SqlParameter> params = new ArrayList<SqlParameter>();
	    params.add(new SqlOutParameter("result", Types.INTEGER));
	    params.add(new SqlParameter("id", Types.VARCHAR));
	    
		final String sql = "{? = call get_customer_balance(?,now())}; ";
		Map<String, Object> outValues = jdbcTemplate.getJdbcOperations().call(		
				new CallableStatementCreator() {
					  public CallableStatement createCallableStatement(Connection con)
					                                          throws SQLException {
					    CallableStatement result = con.prepareCall(sql);
					    result.registerOutParameter(1, Types.DOUBLE);
					    result.setLong(2, id);
					    return result;
					  }
					},params);
//				Arrays.asList(new Object[] {
//							  new SqlOutParameter("result", Types.DOUBLE)}));
				
		
		return (Double)outValues.get("result");
	}
	
	public List<Integer> getListDisabledCustomerIds(){
		
		return jdbcTemplate.query("SELECT * FROM sakila.customer WHERE active = 0" , (rs,rowNum)->{
			
			return new Integer(rs.getInt("customer_id"));
			
		});
	}
	/*
	 * ************************************************************************************************************************************
	 * 
	 * 
	 * 
	 * ============================= Now to the movies ================================
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * ************************************************************************************************************************************
	 */
	
	
	private Movie mapFullMovieRow(ResultSet rs, int rowNumber) throws SQLException{
		Movie movie = new Movie(rs.getInt("film_id"),
								rs.getString("title"), 
								rs.getString("description"), 
								rs.getInt("release_year"), 
								rs.getInt("rental_duration"), 
								rs.getDouble("rental_rate"),
								rs.getDouble("replacement_cost"), 
								rs.getString("rating"), 
								rs.getString("special_features"),
								rs.getString("name")); 
		return movie;
	}
	@Override
	public List<Movie> getMovies() {
		return jdbcTemplate.query("SELECT * FROM film", this::mapFullMovieRow);

	}

	@Override
	public List<Movie> getMoviesAlphabetically() {
		String sql= "SELECT * FROM sakila.film "
				+ "INNER JOIN film_category ON film_category.film_id = film.film_id "
				+ "INNER JOIN category ON category.category_id = film_category.category_id order by title ASC";
		return jdbcTemplate.query(sql, this::mapFullMovieRow);

	}

	@Override
	public List<Movie> getMoviesAlphabetically(int limit, int offset, boolean isDescending) {
		logger.info("getMoviesAlphabetically is called");
		String sql= "SELECT * FROM sakila.film "
				+ "INNER JOIN film_category ON film_category.film_id = film.film_id "
				+ "INNER JOIN category ON category.category_id = film_category.category_id order by title ";
		
		if (isDescending) {
			sql += "DESC LIMIT :LIMIT OFFSET :OFFSET";
		} else {
			sql += "ASC LIMIT :LIMIT OFFSET :OFFSET";
		}
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("LIMIT", limit);
		params.addValue("OFFSET", offset);
		return jdbcTemplate.query(sql, params, this::mapFullMovieRow);
	}

	@Override
	public List<Movie> searchMoviesByTitle(String title, int limit, int offset) {
		
		logger.info("searchMoviesByTitle is called");
		String sql = "SELECT * FROM sakila.film "
				+ "INNER JOIN film_category ON film_category.film_id = film.film_id "
			    + "INNER JOIN category ON category.category_id = film_category.category_id " 
			    + "WHERE film.title LIKE :TITLE order by film.title ASC LIMIT :LIMIT OFFSET :OFFSET"; 
		
		//= "SELECT * FROM SAKILA.film WHERE first_name LIKE :TITLE LIMIT :LIMIT";
		MapSqlParameterSource params = new MapSqlParameterSource();
		title = title + "%";
		logger.info(title);
		params.addValue("TITLE", title);
		params.addValue("LIMIT", limit);
		params.addValue("OFFSET", offset);
		return jdbcTemplate.query(sql, params, this::mapFullMovieRow);
	}

	@Override
	public Movie searchMovieById(int id) {
		logger.info("searchMovieById is called");
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		String sql= "SELECT * FROM sakila.film "
				+ "INNER JOIN film_category ON film_category.film_id = film.film_id "
				+ "INNER JOIN category ON category.category_id = film_category.category_id "
				+ "WHERE film.film_id = :id order by film.title ASC";
		
		return jdbcTemplate.queryForObject(sql, params, this::mapFullMovieRow);
	}

	@Override
	public int numberOfCopiesAvailable(int filmId, int storeId) {
		logger.info("numberOfCopiesAvailable called");
		List<SqlParameter> params = new ArrayList<SqlParameter>();
	    
	    params.add(new SqlParameter("filmId", Types.INTEGER));
	    params.add(new SqlParameter("storeId", Types.INTEGER));
	    params.add(new SqlOutParameter("result", Types.INTEGER));
//	    CALL film_in_stock(1,1,@Count)
		final String sql = "{call film_in_stock(?,?,?)}; ";
		Map<String, Object> outValues = jdbcTemplate.getJdbcOperations().call(		
				new CallableStatementCreator() {
					  public CallableStatement createCallableStatement(Connection con)
					                                          throws SQLException {
					    CallableStatement result = con.prepareCall(sql);
					    result.registerOutParameter(3, Types.INTEGER);
					    result.setLong(1, filmId);
					    result.setLong(2, storeId);
					    return result;
					  }
					},params);
		
		return (int)outValues.get("result");
	}

	@Transactional
	@Override
	public synchronized boolean rentMovie(int customerId, int movieId, int employeeId,int storeId) {
		if(numberOfCopiesAvailable(movieId, storeId)>0){
			logger.info("getting inventoryId");
			String sqlInventoryId = "SELECT inventory_id " 
								     + "FROM inventory "
								     + "WHERE film_id = :FILM_ID "
								     + "AND store_id = :STORE_ID "
								     + "AND inventory_in_stock(inventory_id) "
								     + "LIMIT 1";
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("FILM_ID", movieId);
			params.addValue("STAFF_ID", employeeId);
			params.addValue("STORE_ID", storeId);
			
			int inventoryId = jdbcTemplate.query(sqlInventoryId, params, (rs)->{
				Integer invId = 0;
				while(rs.next()){
					invId = rs.getInt("inventory_id");
				}
				return invId;
			});

			logger.info("inventoryId: "+inventoryId);
			logger.info("Inserting into rental table");
			
			params.addValue("CUSTOMER_ID", customerId);
			params.addValue("INVENTORY_ID", inventoryId);
			
			String sqlRental =	"INSERT INTO rental(rental_date, inventory_id, customer_id, staff_id) "
					+ "VALUES(NOW(), :INVENTORY_ID, :CUSTOMER_ID, :STAFF_ID)";
			
			KeyHolder holder = new GeneratedKeyHolder();
			
			int rowsAffected = jdbcTemplate.update(sqlRental, params, holder);
			logger.info("Rows affected: " + rowsAffected);
//			Long newPersonId = holder.getKey().longValue();
			int keyValue = holder.getKey().intValue();
			params.addValue("LAST_INSERT_ID", keyValue);
			logger.info("KeyValue from sqlRental: " + keyValue);
			
			
			logger.info("getting customer balance");
			double balance = this.getBalance(customerId);
			logger.info("Balance is: " + balance);
			params.addValue("BALANCE", balance);
			
			holder = new GeneratedKeyHolder();
			
			logger.info("Updating payment table");
			String sqlPayment = "INSERT INTO payment (customer_id, staff_id, rental_id, amount,  payment_date) "
								+ "VALUES(:CUSTOMER_ID,:STAFF_ID,:LAST_INSERT_ID, :BALANCE, NOW())";
			rowsAffected = jdbcTemplate.update(sqlPayment, params, holder);
			logger.info("Rows affected: " + rowsAffected);
			logger.info("keyValue is: " +keyValue);
			
			return true;
			
		}
		else{
			logger.warn("Movie was unavailable to rent");
			return false;
		}

//		SELECT @balance := get_customer_balance(3, NOW());

	}
	
	@Override
	public List<Map<String, String>> getRentedMoviesByUser(int id) {

		logger.info("getRentedMoviesByUser is called");
		String sql = "SELECT * FROM rental "
				+ "INNER JOIN customer ON rental.customer_id = customer.customer_id "
				+ "INNER JOIN inventory ON rental.inventory_id = inventory.inventory_id "
				+ "INNER JOIN film ON inventory.film_id = film.film_id "
				+ "where return_date IS NULL AND rental.customer_id = :id";

//		rental_id, rental_date, inventory_id, customer_id, return_date, staff_id, 
//		last_update, customer_id, store_id, first_name, last_name, email, address_id, 
//		active, create_date, last_update, inventory_id, film_id, store_id, last_update, 
//		film_id, title, description, release_year, language_id, original_language_id, rental_duration, 
//		rental_rate, length, replacement_cost, rating, special_features, last_update
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		return jdbcTemplate.query(sql, params, (rs,rowNum) ->{
					Map<String,String> myMap = new HashMap<>();
					myMap.put("title", rs.getString("title"));
					myMap.put("rentalDate",rs.getDate("rental_date").toString());
					myMap.put("rentalId", Integer.toString(rs.getInt("rental_id")));
					myMap.put("customerId",Integer.toString(rs.getInt("customer_id")));
					return myMap;
				});
	}
	
	@Override
	public long getMovieRowCount() {
		return jdbcTemplate.getJdbcOperations().queryForObject("SELECT COUNT(*) FROM film", (rs, rowNum) -> {
			return new Long(rs.getLong(1));
		});
	}
	@Override
	public Map<Integer, String> getFilmCategories() {
		logger.info("getFilmCategories called");
		
		String sql = "SELECT * FROM category";
		 return jdbcTemplate.query(sql, (rs) ->{
			HashMap<Integer,String> myMap = new HashMap<>();
			while(rs.next()){
				myMap.put(rs.getInt("category_id"), rs.getString("name"));
			}
			return myMap;
		 });
	}
	@Override
	public long getMovieRowCountTitleSearch(String movieTitle) {
		logger.info("getMovieRowCountTitleSearch called");
		
		String sql = "SELECT COUNT(*) FROM sakila.film "
				+ "INNER JOIN film_category ON film_category.film_id = film.film_id "
			    + "INNER JOIN category ON category.category_id = film_category.category_id " 
			    + "WHERE film.title LIKE :TITLE";
		
		movieTitle= movieTitle + "%";
		long count = 0;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("TITLE", movieTitle);
		
		count = jdbcTemplate.queryForObject(sql, params, Integer.class);
		
		logger.info("contents of value: " + count);

		return count;
		
		
		}
	@Override
	public int returnMovie(int rentalId) {
		logger.info("inside of returnMovie");

		String sql ="UPDATE rental "
				+ "SET return_date = NOW() "
				+ "WHERE rental_id = :RENTAL_ID";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("RENTAL_ID", rentalId);
		
		int rowsAffected = jdbcTemplate.update(sql, params);
		logger.info(rowsAffected + " row(s) affected by query");
//		int rowsAffected = 1;
		return rowsAffected;
	}
	@Override
	public int payCustomerBalance(int customerId, int rentalId, int staffId, double amount) {
		
//		INSERT INTO payment (customer_id, staff_id, rental_id, amount,  payment_date)
//	    VALUES(3,1,LAST_INSERT_ID(), @balance, NOW());
		
		logger.info("payCustomerBalance called");
		String sql = "INSERT INTO payment (customer_id, staff_id, rental_id, amount, payment_date)" 
				+ "VALUES(:CUSTOMER_ID,:STAFF_ID,:RENTAL_ID,:AMOUNT,NOW())";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("RENTAL_ID", rentalId);
		params.addValue("CUSTOMER_ID", customerId);
		params.addValue("STAFF_ID", staffId);
		params.addValue("AMOUNT", amount);
		
		int rowsAffected = jdbcTemplate.update(sql, params);
		logger.info(rowsAffected + " row(s) affected by query");
		
		return rowsAffected;
	}

	@Override
	public Map<Integer,List<Integer>> getOverdueRentals(){
		String sql = "SELECT customer.customer_id, rental.rental_id "
					+"FROM rental INNER JOIN customer ON rental.customer_id = customer.customer_id "
					+"INNER JOIN address ON customer.address_id = address.address_id "
					+"INNER JOIN inventory ON rental.inventory_id = inventory.inventory_id "
					+"INNER JOIN film ON inventory.film_id = film.film_id "
					+"WHERE rental.return_date IS NULL "
					+"AND rental_date + INTERVAL film.rental_duration DAY < CURRENT_DATE() "
					+" ORDER BY customer.customer_id ASC";
		
		jdbcTemplate.query(sql, (rs) ->{
			HashMap<Integer,List<Integer>> myMap = new HashMap<>();
			
			
			int prevUser = 0;
			int currentUser = -1;
			int currentRental = -1;
			
			while(rs.next()){
				currentUser = rs.getInt("customer_id");
				currentRental = rs.getInt("rental_id");
				if(currentUser == prevUser){
					myMap.get(currentUser).add(currentRental);
				}
				else{
					prevUser = currentUser;
					List<Integer> rentalList = new ArrayList<>();
					rentalList.add(currentRental);
					myMap.put(currentUser, rentalList);
				}
			}
			return myMap;
		});
		
		return null;
	}
	
	@Override
	public List<Map<String,String>> getOverdueRentalsById(int customerId){
		String sql = "SELECT * "
		    + "FROM rental INNER JOIN customer ON rental.customer_id = customer.customer_id "
		    + "INNER JOIN address ON customer.address_id = address.address_id "
		    + "INNER JOIN inventory ON rental.inventory_id = inventory.inventory_id "
		    + "INNER JOIN film ON inventory.film_id = film.film_id "
		    + "WHERE rental.return_date IS NULL "
		    + "AND rental_date + INTERVAL film.rental_duration DAY < CURRENT_DATE() " 
		    + "AND customer.customer_id = :CUSTOMER_ID  ORDER BY customer.customer_id ASC ";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("CUSTOMER_ID", customerId);
		
		return jdbcTemplate.query(sql, params, (rs,rowNum) -> {
			Map<String,String> myMap = new HashMap<>();	
			myMap.put("title", rs.getString("title"));
			myMap.put("rentalDate",rs.getDate("rental_date").toString());
			myMap.put("rentalId", Integer.toString(rs.getInt("rental_id")));
			myMap.put("customerId",Integer.toString(rs.getInt("customer_id")));
			return myMap;
			
			});
	}
	/*
	 * ************************************************************************************************************************************
	 * 
	 * 
	 * 	========================================== STAFF MEMBER FUNCTIONS ======================================================
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * ************************************************************************************************************************************
	 */
	@Override
	public Staff getStaffByUsername(String username) {
		logger.info("getStaffByUsername called");
		String sql = "SELECT staff_id, first_name, last_name, store_id,active, "
						+ "username,staff.password, phone, address, "
						+ "address.postal_code, district, city.city, "
						+ "country.country "
						+ "FROM sakila.staff " 
						+ "INNER JOIN address " 
						+ "ON address.address_id = staff.address_id " 
						+ "INNER JOIN city  "
						+ "ON city.city_id = address.city_id "
						+ "INNER JOIN country "
						+ "ON country.country_id = city.country_id "
						+ "WHERE username = :USERNAME";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("USERNAME", username);
		
		return jdbcTemplate.queryForObject(sql, params,this::staffRowMapper);
		
	}

	private Staff staffRowMapper(ResultSet rs, int rowNumber) throws SQLException{
		Staff staffMember = new Staff(rs.getInt("staff_id"),
				rs.getString("username"),
				rs.getString("first_name"),
				rs.getString("last_name"),
				rs.getInt("store_id"),
				rs.getBoolean("active"),
				rs.getString("password"),
				rs.getString("phone"),
				rs.getString("address"),
				rs.getString("postal_code"),
				rs.getString("district"),
				rs.getString("city"),
				rs.getString("country"));
		return staffMember;
	}

	@Override
	public boolean updateStaffMember(Staff staff) {
		logger.info("updateStaffMember called by: "+staff.getUsername());
		
		String sql = "SELECT staff_id, first_name, last_name, store_id,active, "
				+ "username,staff.password, phone, address, "
				+ "address.postal_code, district, city.city, "
				+ "country.country "
				+ "FROM sakila.staff " 
				+ "INNER JOIN address " 
				+ "ON address.address_id = staff.address_id " 
				+ "INNER JOIN city  "
				+ "ON city.city_id = address.city_id "
				+ "INNER JOIN country "
				+ "ON country.country_id = city.country_id "
				+ "WHERE username = :USERNAME";
		return false;
	}
	@Override
	public boolean updateStaffMemberPassword(String password, int staffId) {
		logger.info("updateStaffMemberPassword called by ID: "+staffId);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("STAFF_ID", staffId);
		params.addValue("PASSWORD", passwordEncoder.encode(password));
		
		return (jdbcTemplate.update("UPDATE staff SET password = :PASSWORD WHERE staff_id = :STAFF_ID", params) ==1);
		
	}
	@Override
	public List<Staff> getStaff() {
		logger.info("getStaffByUsername called");
		String sql = "SELECT staff_id, first_name, last_name, store_id,active, "
						+ "username,staff.password, phone, address, "
						+ "address.postal_code, district, city.city, "
						+ "country.country "
						+ "FROM sakila.staff " 
						+ "INNER JOIN address " 
						+ "ON address.address_id = staff.address_id " 
						+ "INNER JOIN city  "
						+ "ON city.city_id = address.city_id "
						+ "INNER JOIN country "
						+ "ON country.country_id = city.country_id ";
		
		return jdbcTemplate.query(sql, this::staffRowMapper);
	}
	
	
	/*
	 ************************************************************************************************************************************* 
	 * 
	 *
	 * One time functions that are useful because some of the data in the database causes issues.
	 * 	e.g., the function get_balance() in the DB causes an issue because the rented times are 2006 and during it's calculation of 
	 * 		overage fees the variables used cannot hold that large of a number.
	 * 
	 * Also, the built in DB uses a smaller storage for SHA1 hashing and will need to be updated for bcrypt
	 * 
	 *	
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * ************************************************************************************************************************************
	 */
	
	private int updateOldRentals(){
		
		//Included here because I kept getting out of bound errors from the sakila db functions
		// because the original rented dates were in 2006
		String sql = "UPDATE rental SET rental_date = NOW() WHERE return_date IS NULL";
		
		return jdbcTemplate.getJdbcOperations().update(sql);
	}
	private int updatePasswordFieldSize(){
		String sql = "ALTER TABLE `sakila`.`staff` "
				+"CHANGE COLUMN `password` `password` VARCHAR(90) NULL DEFAULT NULL";
		
		return jdbcTemplate.getJdbcOperations().update(sql);
	}
	private void createAuthoritiesTable(){
		String sql = "CREATE TABLE `sakila`.`authorities` ( "
					+ "`authority_id` INT NOT NULL AUTO_INCREMENT, "
					+ "`username` VARCHAR(65) NOT NULL, "
					+ "`authority` VARCHAR(65) NOT NULL, "
					+ "PRIMARY KEY (`authority_id`));";
		jdbcTemplate.getJdbcOperations().execute(sql);
	}
	private int insertTestUser(){
		String sql = "INSERT INTO `sakila`.`staff` "
				+ "(`first_name`, `last_name`, `address_id`, `email`, `store_id`, `active`, `username`, `password`) "
				+ "VALUES ('First', 'Last', '5', 'First.Last@test.com', '1', '1', 'Test', "
				+ "'$2a$12$h.3cOCPCurlhgeoxC6a1MOeIbQWRKhYt3lE2DTeYwf23d6m8neh5a');";
		return jdbcTemplate.getJdbcOperations().update(sql);
	}
	
	private int insertUserRolesIntoAuthority(){
		String sql = "INSERT INTO `sakila`.`authorities` (`username`, `authority`) VALUES ('Test', 'ROLE_ADMIN'); "
					+ "INSERT INTO `sakila`.`authorities` (`username`, `authority`) VALUES ('Mike', 'ROLE_STAFF'); "
					+ "INSERT INTO `sakila`.`authorities` (`username`, `authority`) VALUES ('Jon', 'ROLE_STAFF'); "
					+ "INSERT INTO `sakila`.`authorities` (`username`, `authority`) VALUES ('test', 'ROLE_STAFF');";
		
		return jdbcTemplate.getJdbcOperations().update(sql);
	}

	/*
	 * ************************************************************************************************************************************
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * ************************************************************************************************************************************
	 */
}