package com.example.travelplan.service;

import com.example.travelplan.model.ScheduleItemDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.travelplan.model.ScheduleItem;
import com.example.travelplan.model.User;
import com.example.travelplan.repository.ScheduleItemRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    // スケジュール削除
    @Transactional
    public boolean deleteSchedule(User user, Long scheduleId) {
        Optional<ScheduleItem> opt = repo.findById(scheduleId);
        if (opt.isPresent() && opt.get().getUser().equals(user)) {
            repo.delete(opt.get());
            return true;
        }
        return false;
    }

    // --------------------------------------------------
    // 追加: DTO リストを受け取って displayOrder を更新
    // --------------------------------------------------
    @Transactional
    public void updateOrder(User user, List<ScheduleItemDto> dtoList) {
        // 1) 全スケジュール取得
        List<ScheduleItem> items = repo.findByUserOrderByScheduledAtDesc(user);

        // 2) ID→ScheduleItem のマップを作成
        Map<Long, ScheduleItem> map = items.stream()
                .collect(Collectors.toMap(ScheduleItem::getId, Function.identity()));

        // 3) DTO の order フィールドを displayOrder にセットして保存
        for (ScheduleItemDto dto : dtoList) {
            ScheduleItem item = map.get(dto.getId());
            if (item != null) {
                item.setDisplayOrder(dto.getOrder());
                repo.save(item);
            }
        }
    }
}
