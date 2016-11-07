package com.icrn.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icrn.dao.CustomerDao;
import com.icrn.model.Customer;

@Service
public class CustomerService {

	@Autowired
	private CustomerDao customerDao;
	
	private List<Integer> disabledCustomers = null;
	
	
	public synchronized void updateDisabledCustomerList(){
		this.disabledCustomers = Collections.unmodifiableList(customerDao.getListDisabledCustomerIds());
	}
	
	public List<Integer> getDisabledCustomerIdList(){
		if (disabledCustomers == null) {
            synchronized(this) {
                if (disabledCustomers == null) {
                	this.disabledCustomers = Collections.unmodifiableList(customerDao.getListDisabledCustomerIds());
                }
            }
        }
		return this.disabledCustomers;
	}
	
	public List<Customer> getCustomers() {
		// TODO Auto-generated method stub
		return customerDao.getCustomers();
	}

	
	public List<Customer> getCustomersAlphabetically() {
		// TODO Auto-generated method stub
		return customerDao.getCustomersAlphabetically();
	}

	
	public List<Customer> getCustomersAlphabetically(int limit, int offset, boolean isDescending) {

		return customerDao.getCustomersAlphabetically(limit, offset, isDescending);
	}

	
	public List<Customer> searchCustomersByFirstName(String firstName) {
		return customerDao.searchCustomersByFirstName(firstName,10);
	}

	
	public List<Customer> searchCustomersByLastName(String lastName) {
		return customerDao.searchCustomersByLastName(lastName,10);
	}

	
	public Customer searchCustomerById(int Id) {
		return customerDao.searchCustomerById(Id);
	}

	
	public boolean updateCustomer(Customer customer) {
		return customerDao.updateCustomer(customer);
	}

	
	public boolean deleteCustomer(int id) {
		return customerDao.deleteCustomer(id);
	}
	
	public double getPageCount(int perPageValue){
		
		return Math.ceil((double)customerDao.getRowCount()/perPageValue);
	}
	
	public double getCustomerBalance(int id){
		System.out.println("CALLING GET CUSTOMER BALANCE");
		return customerDao.getBalance(id);
	}

}
