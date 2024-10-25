package com.video.rental.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.video.rental.domain.Customer;
import com.video.rental.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		Customer createdCustomer = customerService.createCustomer(customer);
		return ResponseEntity.status(201).body(createdCustomer);
	}

	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> customers = customerService.getAllCustomers();
		return ResponseEntity.ok(customers);
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId) {
		Customer customer = customerService.getCustomerById(customerId);
		return ResponseEntity.ok(customer);
	}

	@PutMapping("/{customerId}")
	public ResponseEntity<String> updateCustomer(@PathVariable Long customerId, @RequestBody Customer customerDetails) {
		Customer updatedCustomer = customerService.updateCustomer(customerId, customerDetails);
		return ResponseEntity.ok("Customer updated successfully: " + updatedCustomer.getName());
	}

	@DeleteMapping("/{customerId}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
		customerService.deleteCustomer(customerId);
		return ResponseEntity.noContent().build();
	}
}
