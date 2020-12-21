package com.doubleat.ccgame.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameDto {

    @JsonProperty("boardStatus")
    private String boardStatus;

    @JsonProperty("isOver")
    private boolean isOver;

    @JsonProperty("nextTurnUsername")
    private String nextTurnUsername;

}
