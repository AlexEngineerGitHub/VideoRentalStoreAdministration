package com.video.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.video.rental.domain.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

}
