package com.hackathon.server.gpt.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GptResponse {
    @JsonProperty(value = "choices")
    private List<Choice> choices;
}