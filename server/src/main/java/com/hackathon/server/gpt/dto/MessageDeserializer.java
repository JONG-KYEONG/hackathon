package com.hackathon.server.gpt.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.hackathon.server.gpt.dto.request.Content;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageDeserializer extends JsonDeserializer<Message> {

    @Override
    public Message deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        String role = node.get("role").asText();

        // content가 String 형태라면 TextMessage로 처리
        if (node.get("content").isTextual()) {
            String content = node.get("content").asText();
            return new TextMessage(role, content);
        }
        // content가 배열 형태라면 ImageMessage로 처리
        else if (node.get("content").isArray()) {
            List<Content> contentList = new ArrayList<>();
            for (JsonNode contentNode : node.get("content")) {
                contentList.add(new Content(contentNode.asText())); // 예시로 각 content를 문자열로 처리
            }
            return new ImageMessage(role, contentList);
        }
        throw new IOException("Unknown message type");
    }
}