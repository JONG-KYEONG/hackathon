package com.hackathon.server.tour.repository;

import com.hackathon.server.tour.model.TouristAttraction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TouristAttractionRepository extends JpaRepository<TouristAttraction, Long> {

    Optional<TouristAttraction> findByName(String name);
}
