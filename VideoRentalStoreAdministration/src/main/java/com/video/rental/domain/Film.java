package com.video.rental.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "films")
public class Film {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "film_id")
	private Long id;

	@Column(name = "title", nullable = false, length = 100)
	private String title;

	@Enumerated(EnumType.STRING)
	@Column(name = "filmType", nullable = false)
	private FilmType filmType;

	@Column(name = "total_copies", nullable = false)
	private int totalCopies;

	@Column(name = "rented_copies", nullable = false)
	private int rentedCopies;

	public Film() {

	}

	public Film(Long id, String title, FilmType filmType, int totalCopies, int rentedCopies) {
		super();
		this.id = id;
		this.title = title;
		this.filmType = filmType;
		this.totalCopies = totalCopies;
		this.rentedCopies = rentedCopies;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public FilmType getFilmType() {
		return filmType;
	}

	public void setFilmType(FilmType filmType) {
		this.filmType = filmType;
	}

	public int getTotalCopies() {
		return totalCopies;
	}

	public void setTotalCopies(int totalCopies) {
		this.totalCopies = totalCopies;
	}

	public int getRentedCopies() {
		return rentedCopies;
	}

	public void setRentedCopies(int rentedCopies) {
		this.rentedCopies = rentedCopies;
	}
}
