package com.video.rental.service;

import java.util.List;

import com.video.rental.domain.Customer;

public interface CustomerService {

	Customer createCustomer(Customer customer);

	List<Customer> getAllCustomers();

	Customer getCustomerById(Long customerId);

	Customer updateCustomer(Long customerId, Customer customerDetails);

	public void deleteCustomer(Long customerId);

}
