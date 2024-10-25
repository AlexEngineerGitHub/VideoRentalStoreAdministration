package com.video.rental.service.impl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.video.rental.domain.Customer;
import com.video.rental.domain.Film;
import com.video.rental.domain.Rental;
import com.video.rental.exception.FilmNotAvailableException;
import com.video.rental.exception.ResourceNotFoundException;
import com.video.rental.repository.CustomerRepository;
import com.video.rental.repository.FilmRepository;
import com.video.rental.repository.RentalRepository;
import com.video.rental.service.FilmService;

@Service
public class FilmServiceImpl implements FilmService {

	@Autowired
	private FilmRepository filmRepository;

	@Autowired
	private RentalRepository rentalRepository;

	@Autowired
	private CustomerRepository customerRepository;

	public FilmServiceImpl(FilmRepository filmRepository, RentalRepository rentalRepository,
			CustomerRepository customerRepository) {
		this.filmRepository = filmRepository;
		this.rentalRepository = rentalRepository;
		this.customerRepository = customerRepository;
	}

	public Film createFilm(Film film) {
		return filmRepository.save(film);
	}

	public List<Film> getAllFilms() {
		return filmRepository.findAll();
	}

	public Film getFilmById(Long filmId) {
		return filmRepository.findById(filmId)
				.orElseThrow(() -> new ResourceNotFoundException("Film not found with id: " + filmId));
	}

	@Override
	public Film updateFilm(Long filmId, Film filmDetails) {
		Film film = getFilmById(filmId);
		film.setTitle(filmDetails.getTitle());
		film.setFilmType(filmDetails.getFilmType());
		film.setTotalCopies(filmDetails.getTotalCopies());
		film.setRentedCopies(filmDetails.getRentedCopies());

		return filmRepository.save(film);
	}

	public void deleteFilm(Long filmId) {
		Film film = getFilmById(filmId);
		filmRepository.deleteById(film.getId());
	}

	public Rental rentFilm(Long filmId, Long customerId) {
		Film film = getFilmById(filmId);

		if (film.getTotalCopies() <= film.getRentedCopies()) {
			throw new FilmNotAvailableException("No copies available for film: " + film.getTitle());
		}

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + customerId));

		film.setRentedCopies(film.getRentedCopies() + 1);
		filmRepository.save(film);

		Rental rental = new Rental(film, customer, LocalDate.now(), null);

		return rentalRepository.save(rental);
	}

	public Rental returnFilm(Long filmId, Long customerId) {
		List<Rental> rentals = rentalRepository.findByFilmIdAndCustomerIdAndReturnDateIsNull(filmId, customerId);

		if (rentals.isEmpty()) {
			throw new ResourceNotFoundException(
					"Rental not found for film id " + filmId + " and customer id " + customerId);
		}

		Rental rental = rentals.stream().sorted(Comparator.comparing(Rental::getRentalDate)).findFirst()
				.orElseThrow(() -> new ResourceNotFoundException(
						"Rental not found for film id " + filmId + " and customer id " + customerId));

		Film film = rental.getFilm();

		film.setRentedCopies(film.getRentedCopies() - 1);
		filmRepository.save(film);

		rental.setReturnDate(LocalDate.now());

		return rentalRepository.save(rental);
	}
}
