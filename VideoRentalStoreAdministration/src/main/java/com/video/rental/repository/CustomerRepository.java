package com.video.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.video.rental.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
