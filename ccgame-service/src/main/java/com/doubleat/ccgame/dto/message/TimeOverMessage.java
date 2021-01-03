package com.doubleat.ccgame.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TimeOverMessage {
    @JsonProperty("username")
    private String username;
}
