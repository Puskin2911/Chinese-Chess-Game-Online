package com.doubleat.ccgame.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ReadyMessage {
    @JsonProperty("username")
    private String username;
    @JsonProperty("isReady")
    private boolean isReady;
}
