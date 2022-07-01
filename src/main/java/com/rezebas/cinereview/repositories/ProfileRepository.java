package com.rezebas.cinereview.repositories;

import com.rezebas.cinereview.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
