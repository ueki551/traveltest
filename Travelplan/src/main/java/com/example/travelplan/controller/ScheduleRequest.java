package com.example.travelplan.controller;

// リクエストボディ用の DTO
    public  class ScheduleRequest {
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
