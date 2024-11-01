package com.hackathon.server.user.repository;

import com.hackathon.server.user.model.TouristAttraction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TouristAttractionRepository extends JpaRepository<TouristAttraction, Long> {

    Optional<TouristAttraction> findByName(String name);
}
