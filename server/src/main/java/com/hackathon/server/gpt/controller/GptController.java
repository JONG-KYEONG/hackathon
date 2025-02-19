package com.hackathon.server.gpt.controller;

import com.hackathon.server.gpt.dto.ImageUrl;
import com.hackathon.server.gpt.dto.TextMessage;
import com.hackathon.server.gpt.dto.TouristDto;
import com.hackathon.server.gpt.dto.response.GptResponse;
import com.hackathon.server.gpt.service.GptService;
import com.hackathon.server.tour.model.TouristAttraction;
import com.hackathon.server.tour.repository.TouristAttractionRepository;
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

    @GetMapping("/image")
    public TouristDto imageAnalysis(@RequestParam String imageUrl) {
        ImageUrl imageUrl1 = new ImageUrl(imageUrl);
        GptResponse gptResponse = gptService.requestImageAnalysis(imageUrl1);
        TextMessage message = (TextMessage) gptResponse.getChoices().get(0).getMessage();
        String text = message.getContent();

        List<TouristAttraction> touristAttractions = gptService.getCourse(text);

        return TouristDto.builder()
                .touristAttractions(touristAttractions)
                .build();
    }
}