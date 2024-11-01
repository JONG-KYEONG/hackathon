package com.hackathon.server.gpt.controller;

import com.hackathon.server.gpt.dto.ImageUrl;
import com.hackathon.server.gpt.service.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GptController {
    private final GptService gptService;

    @GetMapping("/image")
    public String imageAnalysis(@RequestParam String imageUrl) {
        // TODO: GPT 호출 Service 실행
        ImageUrl imageUrl1 = new ImageUrl(imageUrl);
        gptService.requestImageAnalysis(imageUrl1);
        // TODO: 주변 맛집 Service 실행 ??
        return "";
    }


}