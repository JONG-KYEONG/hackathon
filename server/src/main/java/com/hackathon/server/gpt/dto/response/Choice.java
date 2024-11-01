package com.hackathon.server.gpt.dto.response;

import com.hackathon.server.gpt.dto.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Choice {
    private int index;
    private Message message;
}