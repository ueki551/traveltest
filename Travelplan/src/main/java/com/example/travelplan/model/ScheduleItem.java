package com.example.travelplan.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedule_items")
public class ScheduleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 保存した観光地名
    @Column(nullable = false)
    private String placeName;

    // 保存した住所
    @Column(nullable = false)
    private String placeAddress;

    // 緯度（オプションで保存したい場合）
    private Double latitude;

    // 経度（オプションで保存したい場合）
    private Double longitude;

    // いつ登録したか
    @Column(nullable = false)
    private LocalDateTime scheduledAt;

    // どのユーザーが登録したか（認証済みユーザー）
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // --- ゲッタ／セッタ ----
    public Long getId() { return id; }
    public String getPlaceName() { return placeName; }
    public void setPlaceName(String placeName) { this.placeName = placeName; }
    public String getPlaceAddress() { return placeAddress; }
    public void setPlaceAddress(String placeAddress) { this.placeAddress = placeAddress; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
