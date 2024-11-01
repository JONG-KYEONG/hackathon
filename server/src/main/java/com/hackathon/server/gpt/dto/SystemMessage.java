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
    private String content = "당신은 부산 관광명소 추천 전문가이다."
            + "제공된 사진을 인식하고 어떤 관광지인지 추측하고"
            + "추측한 관광지와 비슷한 부산 관광지를 텍스트로 주어진 부산 관광 명소에서 찾아라."
            + "반환은 반드시 '장소1, 장소2, 장소3, 장소4'의 명사형태로 하라.";
}
