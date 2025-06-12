package com.example.travelplan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.example.travelplan.model.ScheduleItem;
import com.example.travelplan.model.User;
import com.example.travelplan.service.ScheduleService;
import com.example.travelplan.service.UserService;  // 既存の UserService
import com.example.travelplan.repository.UserRepository;

import java.util.List;
import java.util.Optional;

// Thymeleaf＋Ajax で使う場合は @Controller でも OK ですが、
// ここでは簡易に @RestController として実装します。
@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final UserRepository userRepo;

    public ScheduleController(ScheduleService scheduleService, UserRepository userRepo) {
        this.scheduleService = scheduleService;
        this.userRepo = userRepo;
    }

    // JSON で受け取り、スケジュールを保存する
    @PostMapping("/add")
    public ResponseEntity<?> addSchedule(
            @AuthenticationPrincipal UserDetails springUser,
            @RequestBody ScheduleRequest req) {

        // 認証済みユーザーを取得
        Optional<User> optUser = userRepo.findByUsername(springUser.getUsername());
        if (optUser.isEmpty()) {
            return ResponseEntity.status(401).body("ユーザーが見つかりません");
        }
        User user = optUser.get();

        // 座標情報がなければ null を渡しても OK
        Double lat = req.getLatitude();
        Double lng = req.getLongitude();

        // サービス層で保存
        ScheduleItem item = scheduleService.addSchedule(
                user, req.getPlaceName(), req.getPlaceAddress(), lat, lng);

        return ResponseEntity.ok(item);
    }

    // ユーザーのスケジュール一覧を返す（GET で取得）
    @GetMapping("/list")
    public ResponseEntity<List<ScheduleItem>> getScheduleList(
            @AuthenticationPrincipal UserDetails springUser) {

        Optional<User> optUser = userRepo.findByUsername(springUser.getUsername());
        if (optUser.isEmpty()) {
            return ResponseEntity.status(401).build();
        }
        User user = optUser.get();
        List<ScheduleItem> list = scheduleService.getScheduleForUser(user);
        return ResponseEntity.ok(list);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails springUser) {

        // 認証ユーザー取得（既存の userRepo を使って）
        Optional<User> optUser = userRepo.findByUsername(springUser.getUsername());
        if (optUser.isEmpty()) {
            return ResponseEntity.status(401).body("ユーザーが見つかりません");
        }
        User user = optUser.get();

        // サービス層で本人のスケジュールかチェックしつつ削除
        boolean deleted = scheduleService.deleteSchedule(user, id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).body("削除権限がないか、該当データが存在しません");
        }
    }
}


