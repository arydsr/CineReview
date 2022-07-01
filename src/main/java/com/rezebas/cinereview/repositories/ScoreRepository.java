package com.rezebas.cinereview.repositories;

import com.rezebas.cinereview.domain.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    boolean existsByMovieIdAndUsername(String movieId, String username);
    Optional<Score> findByMovieIdAndUsername(String movieId, String username);
    List<Score> findByMovieId(String movieId);
}
