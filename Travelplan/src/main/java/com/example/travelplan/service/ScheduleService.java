package com.example.travelplan.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.travelplan.model.ScheduleItem;
import com.example.travelplan.model.User;
import com.example.travelplan.repository.ScheduleItemRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleItemRepository repo;

    public ScheduleService(ScheduleItemRepository repo) {
        this.repo = repo;
    }

    // 観光地をスケジュールに追加
    @Transactional
    public ScheduleItem addSchedule(User user, String placeName, String placeAddress, Double lat, Double lng) {
        ScheduleItem item = new ScheduleItem();
        item.setUser(user);
        item.setPlaceName(placeName);
        item.setPlaceAddress(placeAddress);
        item.setLatitude(lat);
        item.setLongitude(lng);
        item.setScheduledAt(LocalDateTime.now());
        return repo.save(item);
    }

    // ユーザーのスケジュール一覧取得
    @Transactional(readOnly = true)
    public List<ScheduleItem> getScheduleForUser(User user) {
        return repo.findByUserOrderByScheduledAtDesc(user);
    }
}
