package com.hackathon.server.gpt.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(using = MessageDeserializer.class)
public class Message {
    private String role;
}