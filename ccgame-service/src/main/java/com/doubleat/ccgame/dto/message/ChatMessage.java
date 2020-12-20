package com.doubleat.ccgame.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessage {

    @JsonProperty("username")
    private String username;

    @JsonProperty("message")
    private String message;

}
