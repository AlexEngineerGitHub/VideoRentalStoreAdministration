package com.video.rental.service;

import java.util.List;

import com.video.rental.domain.Film;
import com.video.rental.domain.Rental;

public interface FilmService {

	Film createFilm(Film film);

	List<Film> getAllFilms();

	Film getFilmById(Long filmId);

	Film updateFilm(Long filmId, Film filmDetails);

	void deleteFilm(Long filmId);

	Rental rentFilm(Long filmId, Long customerId);

	Rental returnFilm(Long filmId, Long customerId);
}
