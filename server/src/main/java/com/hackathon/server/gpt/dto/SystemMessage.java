package com.hackathon.server.gpt.dto;

public final class SystemMessage extends Message {
    private final String role = "system";
    private final String content = "당신은 부산 관광명소 추천 전문가이다. 다음 주어진 장소들 중에 사진과 가장 유사한 장소를 반환하라.";
}
