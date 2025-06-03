package com.example.travelplan.controller;

import java.util.List;
import java.util.Optional;

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

    // UserRepository を注入しておく（認証済みユーザーを DB から取ってくるため）
    public AttractionController(ScheduleService scheduleService,
                                UserRepository userRepo) {
        this.scheduleService = scheduleService;
        this.userRepo = userRepo;
    }

    /**
     * 「/search」に GET リクエストが来たときに呼ばれるメソッド。
     * ・認証済みユーザー（UserDetails） → ドメインの User を取得
     * ・scheduleService.getScheduleForUser(...) で ScheduleItem 一覧を取って Model に追加
     * ・未ログイン時は空リストを渡す
     */
    @GetMapping("/search")
    public String showSearchForm(
            @AuthenticationPrincipal UserDetails springUser,  // Spring Security の認証情報
            Model model) {

        List<ScheduleItem> scheduleList;

        if (springUser != null) {
            // springUser.getUsername() がドメインの User.username に対応している想定
            Optional<User> optDomainUser = userRepo.findByUsername(springUser.getUsername());
            if (optDomainUser.isPresent()) {
                User domainUser = optDomainUser.get();
                scheduleList = scheduleService.getScheduleForUser(domainUser);
            } else {
                // 万が一 DB にユーザーがいなかったら空リスト
                scheduleList = List.of();
            }
        } else {
            // springUser が null = 未ログイン時
            scheduleList = List.of();
        }

        // Model に "scheduleList" として必ずセットする
        model.addAttribute("scheduleList", scheduleList);

        // ここで "search" を返すと、src/main/resources/templates/search.html がレンダリングされる
        return "search";
    }
}
