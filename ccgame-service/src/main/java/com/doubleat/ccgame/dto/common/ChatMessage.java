package com.doubleat.ccgame.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class ChatMessage {
    @JsonProperty("username")
    private String username;
    @JsonProperty("message")
    private String message;
}
