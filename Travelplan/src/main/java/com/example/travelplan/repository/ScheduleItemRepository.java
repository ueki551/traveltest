package com.example.travelplan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.travelplan.model.ScheduleItem;
import com.example.travelplan.model.User;
import java.util.List;

public interface ScheduleItemRepository extends JpaRepository<ScheduleItem, Long> {
    // 特定ユーザーのスケジュール一覧を取得するメソッド
    List<ScheduleItem> findByUserOrderByScheduledAtDesc(User user);
}
