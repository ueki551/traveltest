package com.example.travelplan.repository;

// src/main/java/com/example/travelplan/repository/TouristAttractionRepository.java


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.travelplan.model.TouristAttraction;
import java.util.List;

public interface TouristAttractionRepository
        extends JpaRepository<TouristAttraction, Long> {
    List<TouristAttraction> findByNameContainingIgnoreCase(String keyword);
}

