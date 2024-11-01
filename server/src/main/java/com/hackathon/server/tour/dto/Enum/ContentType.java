package com.hackathon.server.tour.dto.Enum;

public enum ContentType {
    관광지(12),
    문화시설(14),
    레포츠(28),
    숙박(32),
    음식점(39);

    private final int value;

    ContentType(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}