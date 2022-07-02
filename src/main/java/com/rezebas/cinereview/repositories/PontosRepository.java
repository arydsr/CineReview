package com.rezebas.cinereview.repositories;

import com.rezebas.cinereview.domain.Pontos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PontosRepository extends JpaRepository<Pontos, Long> {
    boolean existsByMovieIdAndUsername(String movieId, String username);
    Optional<Pontos> findByMovieIdAndUsername(String movieId, String username);
    List<Pontos> findByMovieId(String movieId);
}
