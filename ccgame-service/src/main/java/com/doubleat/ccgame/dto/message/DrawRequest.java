package com.doubleat.ccgame.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DrawRequest {
    @JsonProperty("username")
    private String username;
}
