package com.doubleat.ccgame.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GameDto {

    @JsonProperty("boardStatus")
    private String boardStatus;

    @JsonProperty("isOver")
    private boolean isOver;

}
