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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.video.rental.domain.Film;
import com.video.rental.domain.Rental;
import com.video.rental.service.impl.FilmServiceImpl;

@RestController
@RequestMapping("/api/films")
public class FilmController {

	@Autowired
	private FilmServiceImpl filmService;

	@PostMapping
	public ResponseEntity<Film> createFilm(@RequestBody Film film) {
		Film createdFilm = filmService.createFilm(film);
		return ResponseEntity.status(201).body(createdFilm);
	}

	@GetMapping
	public ResponseEntity<List<Film>> getAllFilms() {
		List<Film> films = filmService.getAllFilms();
		return ResponseEntity.ok(films);
	}

	@GetMapping("/{filmId}")
	public ResponseEntity<Film> getFilmById(@PathVariable Long filmId) {
		Film film = filmService.getFilmById(filmId);
		return ResponseEntity.ok(film);
	}

	@PutMapping("/{filmId}")
	public ResponseEntity<String> updateFilm(@PathVariable Long filmId, @RequestBody Film filmDetails) {
		Film updatedFilm = filmService.updateFilm(filmId, filmDetails);
		return ResponseEntity.ok("Film updated successfully: " + updatedFilm.getTitle());
	}

	@DeleteMapping("/{filmId}")
	public ResponseEntity<Void> deleteFilm(@PathVariable Long filmId) {
		filmService.deleteFilm(filmId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/rent")
	public ResponseEntity<String> rentFilm(@RequestParam Long filmId, @RequestParam Long customerId) {
		Rental rental = filmService.rentFilm(filmId, customerId);
		return ResponseEntity.ok(
				"The film: " + rental.getFilm().getTitle() + " has been rented by: " + rental.getCustomer().getName());
	}

	@PostMapping("/return")
	public ResponseEntity<String> returnFilm(@RequestParam Long filmId, @RequestParam Long customerId) {
		Rental updatedRental = filmService.returnFilm(filmId, customerId);
		return ResponseEntity.ok("The film: " + updatedRental.getFilm().getTitle() + " has been returned by: "
				+ updatedRental.getCustomer().getName());
	}

}
