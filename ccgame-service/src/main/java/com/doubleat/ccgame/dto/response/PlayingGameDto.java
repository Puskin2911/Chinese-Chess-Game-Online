package com.doubleat.ccgame.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayingGameDto {

    @JsonProperty("boardStatus")
    private String boardStatus;

    @JsonProperty("nextTurnUsername")
    private String nextTurnUsername;

    @JsonProperty("redPlayerUsername")
    private String redPlayerUsername;

    @JsonProperty("isGeneralChecking")
    private boolean isGeneralChecking;

    @JsonProperty("moved")
    private String moved;

}
