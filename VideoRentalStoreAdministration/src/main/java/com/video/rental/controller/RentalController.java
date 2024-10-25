package com.video.rental.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.video.rental.domain.Rental;
import com.video.rental.service.RentalService;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

	@Autowired
	private RentalService rentalService;

	@GetMapping
	public ResponseEntity<List<Rental>> getAllRentals() {
		List<Rental> rentals = rentalService.getAllRentals();
		return ResponseEntity.ok(rentals);
	}
}
