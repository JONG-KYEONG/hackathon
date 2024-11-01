package com.hackathon.server.gpt.service;

import com.hackathon.server.gpt.dto.ImageUrl;
import com.hackathon.server.gpt.dto.response.GptResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GptService {
    @Value("${openai.model}")
    private String apiModel;

    @Value("${openai.api.url}")
    private String apiUrl;

    private final RestTemplate template;

    private final String requestText = "[감천문화마을, 흰여울문화마을, 해운대해수욕장, "
            + "광안리해수욕장, 태종대 유원지, 용두산공원과 부산타워, 송도 해상 케이블카, BIFF 광장과 국제시장,"
            + " 자갈치시장, 해동용궁사, 오륙도 스카이워크, 송정해수욕장, 장산 등산로, 아미산 전망대, 다대포 꿈의 낙조분수,"
            + " 국립해양박물관, 누리마루 APEC 하우스, 부산시립미술관, 동백섬, 장림포구 부네치아, 온천천 카페거리, 범어사,"
            + " 금정산성, 해운대 블루라인파크, 부산현대미술관, 송상현 광장, UN기념공원, 부산영화의전당, 동래읍성, 부산시민공원]";

    public GptResponse requestImageAnalysis(ImageUrl imageUrl) {
        GptRequest request = GptRequest.createImageRequest(apiModel, 500, "user", requestText, imageUrl);

        return template.postForObject(apiUrl, request, GptResponse.class);
    }
}