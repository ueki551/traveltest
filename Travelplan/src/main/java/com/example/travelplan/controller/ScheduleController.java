package com.example.travelplan.controller;

import com.example.travelplan.model.ScheduleItemDto;
import com.example.travelplan.model.ScheduleItem;
import com.example.travelplan.model.User;
import com.example.travelplan.repository.UserRepository;
import com.example.travelplan.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedule")
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

        Optional<User> optUser = userRepo.findByUsername(springUser.getUsername());
        if (optUser.isEmpty()) {
            return ResponseEntity.status(401).body("ユーザーが見つかりません");
        }
        User user = optUser.get();

        ScheduleItem item = scheduleService.addSchedule(
                user,
                req.getPlaceName(),
                req.getPlaceAddress(),
                req.getLatitude(),
                req.getLongitude()
        );

        return ResponseEntity.ok(item);
    }

    // ユーザーのスケジュール一覧を返す
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

    // スケジュール項目の削除
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails springUser) {

        Optional<User> optUser = userRepo.findByUsername(springUser.getUsername());
        if (optUser.isEmpty()) {
            return ResponseEntity.status(401).body("ユーザーが見つかりません");
        }
        User user = optUser.get();

        boolean deleted = scheduleService.deleteSchedule(user, id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).body("削除権限がないか、該当データが存在しません");
        }
    }

    /**
     * 並べ替え後の DTO リスト（{ id, order }）を受け取って
     * displayOrder を更新するエンドポイント
     */
    @PostMapping("/reorder")
    public ResponseEntity<Void> reorder(
            @AuthenticationPrincipal UserDetails springUser,
            @RequestBody List<ScheduleItemDto> items  // <- Long から ScheduleItemDto に変更
    ) {
        Optional<User> optUser = userRepo.findByUsername(springUser.getUsername());
        if (optUser.isEmpty()) {
            return ResponseEntity.status(401).build();
        }
        User user = optUser.get();

        // DTO.getId() / DTO.getOrder() を使って更新
        scheduleService.updateOrder(user, items);
        return ResponseEntity.ok().build();
    }
}
