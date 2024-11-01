package com.hackathon.server.gpt.dto;

import com.hackathon.server.gpt.dto.request.Content;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageMessage extends Message {
    private String role;
    private List<Content> content;
}