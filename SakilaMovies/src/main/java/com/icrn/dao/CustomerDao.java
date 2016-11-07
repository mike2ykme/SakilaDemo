package com.icrn.dao;

import java.util.List;

import com.icrn.model.Customer;

public interface CustomerDao {
	List<Customer> getCustomers();
	List<Customer> getCustomersAlphabetically();
	List<Customer> getCustomersAlphabetically(int limit, int offset, boolean isDescending);
	List<Customer> searchCustomersByFirstName(String firstName, int limit);
	List<Customer> searchCustomersByLastName(String lastName, int limit);
	Customer searchCustomerById(int id);
	boolean updateCustomer(Customer customer);
	boolean deleteCustomer(int id);
	double getBalance(int id);
	long getRowCount();
	List<Integer> getListDisabledCustomerIds();
//	int insertCustomer(Customer customer);
	
}
