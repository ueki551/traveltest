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

    // リクエストボディ用の DTO
    public static class ScheduleRequest {
        private String placeName;
        private String placeAddress;
        private Double latitude;
        private Double longitude;
        // getter / setter
        public String getPlaceName() { return placeName; }
        public void setPlaceName(String placeName) { this.placeName = placeName; }
        public String getPlaceAddress() { return placeAddress; }
        public void setPlaceAddress(String placeAddress) { this.placeAddress = placeAddress; }
        public Double getLatitude() { return latitude; }
        public void setLatitude(Double latitude) { this.latitude = latitude; }
        public Double getLongitude() { return longitude; }
        public void setLongitude(Double longitude) { this.longitude = longitude; }
    }
}
