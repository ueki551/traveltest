package com.example.travelplan.service;
// src/main/java/com/example/travelplan/service/TouristAttractionService.java


import org.springframework.stereotype.Service;
import com.example.travelplan.repository.TouristAttractionRepository;
import com.example.travelplan.model.TouristAttraction;
import java.util.List;

@Service
public class TouristAttractionService {
    private final TouristAttractionRepository repo;

    public TouristAttractionService(TouristAttractionRepository repo) {
        this.repo = repo;
    }

    public List<TouristAttraction> searchByName(String keyword) {
        return repo.findByNameContainingIgnoreCase(keyword);
    }
}
