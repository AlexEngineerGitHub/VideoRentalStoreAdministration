package com.video.rental.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.video.rental.domain.Customer;
import com.video.rental.domain.Film;
import com.video.rental.domain.Rental;
import com.video.rental.repository.RentalRepository;
import com.video.rental.service.RentalService;
import com.video.rental.service.impl.RentalServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class RentalServiceTest {

	private RentalService rentalService;

	private RentalRepository rentalRepository;

	private Rental rental1, rental2;
	private Film film1, film2;
	private Customer customer1, customer2;

	@BeforeEach
	void setUp() {
		rentalRepository = mock(RentalRepository.class);
		rentalService = new RentalServiceImpl(rentalRepository);

		film1 = new Film();
		film1.setId(1L);

		film2 = new Film();
		film2.setId(2L);

		customer1 = new Customer();
		customer1.setId(1L);

		customer2 = new Customer();
		customer2.setId(2L);

		rental1 = new Rental();
		rental1.setId(1L);
		rental1.setFilm(film1);
		rental1.setCustomer(customer1);
		rental1.setRentalDate(LocalDate.now());

		rental2 = new Rental();
		rental2.setId(2L);
		rental2.setFilm(film2);
		rental2.setCustomer(customer2);
		rental2.setRentalDate(LocalDate.now());
	}

	@Test
	void testGetAllRentals() {
		when(rentalRepository.findAll()).thenReturn(Arrays.asList(rental1, rental2));

		List<Rental> rentals = rentalService.getAllRentals();

		assertNotNull(rentals);
		assertEquals(2, rentals.size());
		assertEquals(rental1.getId(), rentals.get(0).getId());
		assertEquals(rental2.getId(), rentals.get(1).getId());

		assertEquals(rental1.getFilm().getId(), film1.getId());
		assertEquals(rental1.getCustomer().getId(), customer1.getId());
		assertEquals(rental2.getFilm().getId(), film2.getId());
		assertEquals(rental2.getCustomer().getId(), customer2.getId());

		verify(rentalRepository, times(1)).findAll();
	}

}
