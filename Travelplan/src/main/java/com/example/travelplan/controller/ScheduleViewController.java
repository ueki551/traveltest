package com.example.travelplan.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.travelplan.model.ScheduleItem;
import com.example.travelplan.model.User;
import com.example.travelplan.service.ScheduleService;

@Controller
public class ScheduleViewController {

    private final ScheduleService scheduleService;

    public ScheduleViewController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * 画面表示用のエンドポイント
     * ブラウザから /schedule にアクセスすると、Thymeleaf テンプレート schedule.html をレンダリングする
     */
    @GetMapping("/schedule")
    public String showSchedulePage(
            @AuthenticationPrincipal User user,  // 認証済みUserが入る
            Model model) {

        if (user != null) {
            // ログインユーザーのスケジュール一覧を取得して model に詰める
            List<ScheduleItem> scheduleList = scheduleService.getScheduleForUser(user);
            model.addAttribute("scheduleList", scheduleList);
        }
        // src/main/resources/templates/schedule.html を探しに行く
        return "schedule";
    }
}
