package com.rezebas.cinereview.repositories;

import com.rezebas.cinereview.domain.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
}
