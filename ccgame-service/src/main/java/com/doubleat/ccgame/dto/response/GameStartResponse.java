package com.doubleat.ccgame.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class GameStartResponse {

    @JsonProperty("redPlayerUsername")
    private String redPlayerUsername;

}
