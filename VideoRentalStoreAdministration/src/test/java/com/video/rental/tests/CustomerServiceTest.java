package com.video.rental.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.video.rental.domain.Customer;
import com.video.rental.exception.ResourceNotFoundException;
import com.video.rental.repository.CustomerRepository;
import com.video.rental.service.CustomerService;
import com.video.rental.service.impl.CustomerServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

	private CustomerService customerService;

	private CustomerRepository customerRepository;

	private Customer customer;

	@BeforeEach
	void setUp() {
		customerRepository = mock(CustomerRepository.class);
		customerService = new CustomerServiceImpl(customerRepository);
		customer = new Customer();
		customer.setId(1L);
		customer.setName("Juan");
	}

	@Test
	public void testCreateCustomer() {
		when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		Customer createdCustomer = customerService.createCustomer(customer);

		assertNotNull(createdCustomer);
		assertEquals("Juan", createdCustomer.getName());
		verify(customerRepository, times(1)).save(customer);
	}

	@Test
	void testGetAllCustomers() {
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer(1L, "Juan"));
		customers.add(new Customer(2L, "Pepe"));

		when(customerRepository.findAll()).thenReturn(customers);

		List<Customer> allCustomers = customerService.getAllCustomers();
		assertEquals(2, allCustomers.size());
	}

	@Test
	public void testGetCustomerById() {
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

		Customer foundCustomer = customerService.getCustomerById(1L);

		assertNotNull(foundCustomer);
		assertEquals("Juan", foundCustomer.getName());
		verify(customerRepository, times(1)).findById(1L);
	}

	@Test
	public void testGetCustomerByIdNotFound() {
		when(customerRepository.findById(2L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			customerService.getCustomerById(2L);
		});
	}

	@Test
	public void testUpdateCustomer() {
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		customer.setName("Juan");
		Customer updatedCustomer = customerService.updateCustomer(1L, customer);

		assertNotNull(updatedCustomer);
		assertEquals("Juan", updatedCustomer.getName());
		verify(customerRepository, times(1)).save(customer);
	}

	@Test
	public void testUpdateCustomerNotFound() {
		when(customerRepository.findById(2L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			customerService.updateCustomer(2L, customer);
		});
	}

	@Test
	public void testDeleteCustomer() {
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

		customerService.deleteCustomer(1L);

		verify(customerRepository, times(1)).deleteById(1L);

	}

	@Test
	public void testDeleteCustomerNotFound() {
		when(customerRepository.findById(2L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			customerService.deleteCustomer(2L);
		});
	}
}
