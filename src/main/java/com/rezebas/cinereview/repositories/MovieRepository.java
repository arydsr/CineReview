package com.rezebas.cinereview.repositories;

import com.rezebas.cinereview.domain.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Film, Long> {
}
