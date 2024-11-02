package com.hackathon.server.gpt.dto.request;

import com.hackathon.server.gpt.dto.ImageUrl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ImageContent extends Content {
    private ImageUrl image_url;

    public ImageContent(String type, ImageUrl image_url) {
        super(type);
        this.image_url = image_url;
    }
}