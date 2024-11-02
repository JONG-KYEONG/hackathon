package com.hackathon.server.gpt.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackathon.server.gpt.dto.ImageMessage;
import com.hackathon.server.gpt.dto.ImageUrl;
import com.hackathon.server.gpt.dto.Message;
import com.hackathon.server.gpt.dto.SystemMessage;
import com.hackathon.server.gpt.dto.request.Content;
import com.hackathon.server.gpt.dto.request.ImageContent;
import com.hackathon.server.gpt.dto.request.TextContent;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GptRequest {
    @JsonProperty("model")
    private String model;
    @JsonProperty("messages")
    private List<Message> messages;
    @JsonProperty("max_tokens")
    private int maxTokens;

    public static GptRequest createImageRequest(String model, int maxTokens, String role, String requestText,
                                                ImageUrl imageUrl) {
        TextContent textContent = new TextContent("text", requestText);
        ImageContent imageContent = new ImageContent("image_url", imageUrl);
        List<Content> list = List.of(textContent, imageContent);
        Message message = new ImageMessage(role, list);
        return createGptRequest(model, maxTokens, List.of(new SystemMessage(), message));
    }

    private static GptRequest createGptRequest(String model, int maxTokens, List<Message> messages) {

        return GptRequest.builder()
                .model(model)
                .maxTokens(maxTokens)
                .messages(messages)
                .build();
    }
}