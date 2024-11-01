package com.hackathon.server.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SystemMessage extends Message {
    private String role = "system";
    private String content = """
            당신은 부산 관광명소 추천 전문가이다.
            다음 주어진 장소들 중에 사진과 가장 유사한 4개의 장소를 다른 말 없이 [장소1, 장소2, 장소3, 장소4]의 명사형태로
             []를 포함하지 않고 반환하라.
            """;
}
