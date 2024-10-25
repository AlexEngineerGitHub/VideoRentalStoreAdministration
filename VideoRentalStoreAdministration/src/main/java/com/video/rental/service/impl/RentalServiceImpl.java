package com.video.rental.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.video.rental.domain.Rental;
import com.video.rental.repository.RentalRepository;
import com.video.rental.service.RentalService;

@Service
public class RentalServiceImpl implements RentalService {

	@Autowired
	private RentalRepository rentalRepository;

	public RentalServiceImpl(RentalRepository rentalRepository) {
		this.rentalRepository = rentalRepository;
	}

	public List<Rental> getAllRentals() {
		return rentalRepository.findAll();
	}

}
