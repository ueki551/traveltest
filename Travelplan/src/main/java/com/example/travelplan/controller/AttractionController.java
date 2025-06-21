package com.example.travelplan.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.travelplan.model.ScheduleItem;
import com.example.travelplan.model.User;
import com.example.travelplan.repository.UserRepository;
import com.example.travelplan.service.ScheduleService;

@Controller
public class AttractionController {

    private final ScheduleService scheduleService;
    private final UserRepository userRepo;

    // application.properties の external.api.key を読み込む
    @Value("${external.api.key}")
    private String apiKey;

    public AttractionController(ScheduleService scheduleService,
                                UserRepository userRepo) {
        this.scheduleService = scheduleService;
        this.userRepo = userRepo;
    }

    @GetMapping("/search")
    public String showSearchForm(
            @AuthenticationPrincipal UserDetails springUser,  // Spring Security の認証情報
            Model model) {

        List<ScheduleItem> scheduleList;

        if (springUser != null) {
            Optional<User> optDomainUser = userRepo.findByUsername(springUser.getUsername());
            if (optDomainUser.isPresent()) {
                User domainUser = optDomainUser.get();
                scheduleList = scheduleService.getScheduleForUser(domainUser);
            } else {
                scheduleList = List.of();
            }
        } else {
            scheduleList = List.of();
        }

        // 既存のスケジュールリスト
        model.addAttribute("scheduleList", scheduleList);
        // APIキーも Model に追加
        model.addAttribute("apiKey", apiKey);

        return "search";
    }

}
