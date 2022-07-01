package com.rezebas.cinereview.repositories;

import com.rezebas.cinereview.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
