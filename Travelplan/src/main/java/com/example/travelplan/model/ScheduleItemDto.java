package com.example.travelplan.model;



import com.example.travelplan.model.ScheduleItem;
import java.time.LocalDateTime;

/**
 * ScheduleItem エンティティをクライアントに返却するための DTO クラス
 */
public class ScheduleItemDto {
    private Long id;
    private String placeName;
    private String placeAddress;
    private Double latitude;
    private Double longitude;
    private LocalDateTime scheduledAt;
    private Integer displayOrder;
    private Integer order;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public ScheduleItemDto() {
    }

    public ScheduleItemDto(Long id,
                           String placeName,
                           String placeAddress,
                           Double latitude,
                           Double longitude,
                           LocalDateTime scheduledAt,
                           Integer displayOrder) {
        this.id = id;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.scheduledAt = scheduledAt;
        this.displayOrder = displayOrder;
    }

    // --- getters & setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * ScheduleItem から DTO に変換するユーティリティメソッド
     */
    public static ScheduleItemDto from(ScheduleItem item) {
        return new ScheduleItemDto(
                item.getId(),
                item.getPlaceName(),
                item.getPlaceAddress(),
                item.getLatitude(),
                item.getLongitude(),
                item.getScheduledAt(),
                item.getDisplayOrder()
        );
    }
}
