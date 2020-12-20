package com.doubleat.ccgame.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ReadyMessage {
    @JsonProperty("username")
    private String username;
    @JsonProperty("isReady")
    private boolean isReady;

    @Override public String toString() {
        return "ReadyMessage{" +
                "username='" + username + '\'' +
                ", isReady=" + isReady +
                '}';
    }
}
