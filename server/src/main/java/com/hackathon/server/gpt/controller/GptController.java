package com.hackathon.server.gpt.controller;

import com.hackathon.server.gpt.dto.ImageUrl;
import com.hackathon.server.gpt.dto.TextMessage;
import com.hackathon.server.gpt.dto.response.GptResponse;
import com.hackathon.server.gpt.service.GptService;
import com.hackathon.server.user.model.TouristAttraction;
import com.hackathon.server.user.repository.TouristAttractionRepository;
import java.util.ArrayList;
import java.util.List;
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
    private final TouristAttractionRepository touristAttractionRepository;

    @GetMapping("/image")
    public List<TouristAttraction> imageAnalysis(@RequestParam String imageUrl) {
        // TODO: GPT 호출 Service 실행
        ImageUrl imageUrl1 = new ImageUrl(imageUrl);
        GptResponse gptResponse = gptService.requestImageAnalysis(imageUrl1);
        TextMessage message = (TextMessage) gptResponse.getChoices().get(0).getMessage();
        String text = message.getContent();
        System.out.println("text = " + text);

        List<TouristAttraction> touristAttractions = new ArrayList<>();

        String[] places = text.split(",");
        for (String place : places) {
            touristAttractions.add(
                    touristAttractionRepository.findByName(place)
                            .orElseThrow());
        }

        return touristAttractions;
    }


}