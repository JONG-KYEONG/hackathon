package com.hackathon.server.gpt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class TextContent extends Content {
    private String text;

    public TextContent(String type, String text) {
        super(type);
        this.text = text;
    }
}