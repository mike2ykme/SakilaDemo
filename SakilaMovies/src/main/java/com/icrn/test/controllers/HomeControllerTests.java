package com.icrn.test.controllers;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.icrn.controllers.HomeController;
import com.icrn.model.Customer;
import com.icrn.service.CustomerService;

public class HomeControllerTests {

	@Test
	public void testHomePage() throws Exception{
		HomeController controller = new HomeController();
		MockMvc mockMvc = standaloneSetup(controller).build();
		
		mockMvc.perform(get("/"))
				.andExpect(view().name("home"));
	}
	
	@Test
	public void testCustomerSearch() throws Exception{
		
		List<Customer> listOfCustomers = null;
		CustomerService mockCS= mock(CustomerService.class);
		when(mockCS.getCustomersAlphabetically(25, 10, false)).thenReturn(null);
		
	}
}
