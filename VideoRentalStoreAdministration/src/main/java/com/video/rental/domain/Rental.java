package com.video.rental.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rentals")
public class Rental {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "film_id", nullable = false)
	private Film film;

	@Column(name = "rentalDate", nullable = false)
	private LocalDate rentalDate;

	@Column(name = "returnDate", nullable = true)
	private LocalDate returnDate;

	public Rental() {

	}

	public Rental(Film film, Customer customer, LocalDate rentalDate, LocalDate returnDate) {
		super();
		this.customer = customer;
		this.film = film;
		this.rentalDate = rentalDate;
		this.returnDate = returnDate;
	}

	public Rental(Long id, Film film, Customer customer, LocalDate rentalDate, LocalDate returnDate) {
		super();
		this.id = id;
		this.customer = customer;
		this.film = film;
		this.rentalDate = rentalDate;
		this.returnDate = returnDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public LocalDate getRentalDate() {
		return rentalDate;
	}

	public void setRentalDate(LocalDate rentalDate) {
		this.rentalDate = rentalDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

}
