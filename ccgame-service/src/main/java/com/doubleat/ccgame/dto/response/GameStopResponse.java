package com.doubleat.ccgame.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameStopResponse {
    @JsonProperty("isRedWin")
    private boolean isRedWin;
}
