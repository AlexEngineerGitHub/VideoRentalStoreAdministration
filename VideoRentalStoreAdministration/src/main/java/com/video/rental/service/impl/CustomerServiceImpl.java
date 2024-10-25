package com.video.rental.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.video.rental.domain.Customer;
import com.video.rental.exception.ResourceNotFoundException;
import com.video.rental.repository.CustomerRepository;
import com.video.rental.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	public Customer getCustomerById(Long customerId) {
		return customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
	}

	public Customer updateCustomer(Long customerId, Customer customerDetails) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + customerId));
		customer.setName(customerDetails.getName());
		return customerRepository.save(customer);
	}

	public void deleteCustomer(Long customerId) {
		Customer customer = getCustomerById(customerId);
		customerRepository.deleteById(customer.getId());
	}

}
