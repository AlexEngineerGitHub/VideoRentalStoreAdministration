package com.video.rental.tests;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.video.rental.domain.Customer;
import com.video.rental.domain.Film;
import com.video.rental.domain.FilmType;
import com.video.rental.domain.Rental;
import com.video.rental.exception.FilmNotAvailableException;
import com.video.rental.exception.ResourceNotFoundException;
import com.video.rental.repository.CustomerRepository;
import com.video.rental.repository.FilmRepository;
import com.video.rental.repository.RentalRepository;
import com.video.rental.service.FilmService;
import com.video.rental.service.impl.FilmServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FilmServiceTest {

	private FilmService filmService;
	private FilmRepository filmRepository;
	private RentalRepository rentalRepository;
	private CustomerRepository customerRepository;

	@BeforeEach
	void setUp() {
		filmRepository = mock(FilmRepository.class);
		rentalRepository = mock(RentalRepository.class);
		customerRepository = mock(CustomerRepository.class);
		filmService = new FilmServiceImpl(filmRepository, rentalRepository, customerRepository);
	}

	@Test
	void testCreateFilm() {
		Film film = new Film();
		film.setId(1L);
		film.setTitle("Avatar");
		film.setTotalCopies(10);
		film.setRentedCopies(0);

		when(filmRepository.save(any(Film.class))).thenReturn(film);

		Film createdFilm = filmService.createFilm(film);
		assertEquals("Avatar", createdFilm.getTitle());
		assertEquals(10, createdFilm.getTotalCopies());
	}

	@Test
	void testGetAllFilms() {
		List<Film> films = new ArrayList<>();
		films.add(new Film(1L, "Avatar", FilmType.REGULAR_FILM, 10, 0));
		films.add(new Film(2L, "Avatar 2", FilmType.NEW_RELEASE, 5, 1));

		when(filmRepository.findAll()).thenReturn(films);

		List<Film> allFilms = filmService.getAllFilms();
		assertEquals(2, allFilms.size());
	}

	@Test
	void testGetFilmById() {
		Film film = new Film(1L, "Avatar", FilmType.REGULAR_FILM, 10, 0);
		when(filmRepository.findById(1L)).thenReturn(Optional.of(film));

		Film foundFilm = filmService.getFilmById(1L);
		assertEquals("Avatar", foundFilm.getTitle());
	}

	@Test
	void testGetFilmByIdNotFound() {
		when(filmRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> filmService.getFilmById(1L));
	}

	@Test
	void testUpdateFilm() {
		Film existingFilm = new Film(1L, "Avatar", FilmType.REGULAR_FILM, 10, 0);
		when(filmRepository.findById(1L)).thenReturn(Optional.of(existingFilm));

		Film updatedFilm = new Film(1L, "Avatar Updated", FilmType.REGULAR_FILM, 12, 0);
		when(filmRepository.save(any(Film.class))).thenReturn(updatedFilm);

		Film result = filmService.updateFilm(1L, updatedFilm);
		assertEquals("Avatar Updated", result.getTitle());
	}

	@Test
	void testDeleteFilm() {
		Film film = new Film(1L, "Avatar", FilmType.REGULAR_FILM, 10, 0);
		when(filmRepository.findById(1L)).thenReturn(Optional.of(film));

		filmService.deleteFilm(1L);
		verify(filmRepository, times(1)).deleteById(1L);
	}

	@Test
	void testDeleteFilmNotFound() {
		when(filmRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> filmService.deleteFilm(1L));

		verify(filmRepository, never()).deleteById(1L);
	}

	@Test
	void testRentFilmWithCustomer() {
		Film film = new Film();
		film.setId(1L);
		film.setTotalCopies(5);
		film.setRentedCopies(0);

		Customer customer = new Customer();
		customer.setId(1L);

		Rental rental = new Rental(film, customer, LocalDate.now(), null);

		when(filmRepository.findById(1L)).thenReturn(Optional.of(film));
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		when(rentalRepository.save(any(Rental.class))).thenReturn(rental); // Mock saving the rental

		Rental rentedRental = filmService.rentFilm(1L, 1L);

		assertNotNull(rentedRental); // Assert that the returned rental is not null
		assertEquals(film, rentedRental.getFilm());
		assertEquals(customer, rentedRental.getCustomer());
		assertEquals(1, film.getRentedCopies());
	}

	@Test
	void testRentFilmNotFound() {
		when(filmRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> filmService.rentFilm(1L, 1L));
		verify(filmRepository, never()).save(any(Film.class));
		verify(rentalRepository, never()).save(any(Rental.class));
	}

	@Test
	void testRentFilmCustomerNotFound() {
		Film film = new Film(1L, "Avatar", FilmType.REGULAR_FILM, 10, 2);

		when(filmRepository.findById(1L)).thenReturn(Optional.of(film));
		when(customerRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> filmService.rentFilm(1L, 1L));

		verify(rentalRepository, never()).save(any(Rental.class));
		verify(filmRepository, never()).save(any(Film.class));
	}

	@Test
	void testRentFilmNoAvailableCopies() {
		Film film = new Film(1L, "Avatar", FilmType.REGULAR_FILM, 10, 10);
		Customer customer = new Customer(1L, "Avatar");

		when(filmRepository.findById(1L)).thenReturn(Optional.of(film));
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

		assertThrows(FilmNotAvailableException.class, () -> filmService.rentFilm(1L, 1L));

		verify(rentalRepository, never()).save(any(Rental.class));
		verify(filmRepository, never()).save(any(Film.class));
	}

	@Test
	void testReturnFilmWithDatesAndCustomer() {
		Film film = new Film();
		film.setId(1L);
		film.setTotalCopies(5);
		film.setRentedCopies(2);

		Customer customer = new Customer();
		customer.setId(1L);

		Rental rental = new Rental(film, customer, LocalDate.now().minusDays(5), null);

		when(rentalRepository.findByFilmIdAndCustomerIdAndReturnDateIsNull(1L, 1L)).thenReturn(List.of(rental));
		when(filmRepository.findById(1L)).thenReturn(Optional.of(film));
		when(rentalRepository.save(any(Rental.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Rental returnedRental = filmService.returnFilm(1L, 1L);

		assertNotNull(returnedRental);
		assertEquals(film, returnedRental.getFilm());
		assertEquals(customer, returnedRental.getCustomer());
		assertNotNull(returnedRental.getReturnDate());
		assertEquals(1, film.getRentedCopies());
	}

	@Test
	void testReturnFilmNotFound() {
		when(filmRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> filmService.returnFilm(1L, 1L));
		verify(filmRepository, never()).save(any(Film.class));
		verify(rentalRepository, never()).save(any(Rental.class));
	}

	@Test
	void testReturnCustomerNotFound() {
		Film film = new Film(1L, "Avatar", FilmType.REGULAR_FILM, 10, 1);
		when(filmRepository.findById(1L)).thenReturn(Optional.of(film));
		when(customerRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> filmService.returnFilm(1L, 1L));
		verify(filmRepository, never()).save(any(Film.class));
		verify(rentalRepository, never()).save(any(Rental.class));
	}

	@Test
	void testReturnRentalNotFound() {
		Film film = new Film(1L, "Avatar", FilmType.REGULAR_FILM, 10, 1);
		Customer customer = new Customer(1L, "Juan");
		when(filmRepository.findById(1L)).thenReturn(Optional.of(film));
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		when(rentalRepository.findByFilmIdAndCustomerIdAndReturnDateIsNull(1L, 1L)).thenReturn(List.of());

		assertThrows(ResourceNotFoundException.class, () -> filmService.returnFilm(1L, 1L));
		verify(filmRepository, never()).save(any(Film.class));
		verify(rentalRepository, never()).save(any(Rental.class));
	}
}