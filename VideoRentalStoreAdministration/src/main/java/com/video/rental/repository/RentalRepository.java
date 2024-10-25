package com.video.rental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.video.rental.domain.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
	List<Rental> findByFilmIdAndCustomerIdAndReturnDateIsNull(Long filmId, Long customerId);
}
