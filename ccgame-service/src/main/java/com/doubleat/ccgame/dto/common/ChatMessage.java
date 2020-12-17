package com.doubleat.ccgame.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatMessage {
    @JsonProperty("username")
    private String username;
    @JsonProperty("message")
    private String message;
}
