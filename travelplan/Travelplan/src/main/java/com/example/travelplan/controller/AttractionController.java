package com.example.travelplan.controller;

// src/main/java/com/example/travelplan/controller/AttractionController.java


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.travelplan.service.TouristAttractionService;

@Controller
public class AttractionController {
    private final TouristAttractionService service;

    public AttractionController(TouristAttractionService service) {
        this.service = service;
    }

    @GetMapping("/search")
    public String showSearchForm() {
        return "search";  // search.html
    }

    @GetMapping("/search/results")
    public String search(
            @RequestParam String keyword,
            Model model) {
        model.addAttribute("attractions", service.searchByName(keyword));
        return "search-results";  // search-results.html
    }
}

