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

    public GptResponse requestImageAnalysis(ImageUrl imageUrl) {
        String requestText = "";
        GptRequest request = GptRequest.createImageRequest(apiModel, 500, "user", requestText, imageUrl);
        return template.postForObject(apiUrl, request, GptResponse.class);
    }
}