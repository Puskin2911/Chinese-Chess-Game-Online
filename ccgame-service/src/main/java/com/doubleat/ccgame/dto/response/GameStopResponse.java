package com.doubleat.ccgame.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GameStopResponse {

    @JsonProperty("winner")
    private UserDto winner;

    @JsonProperty("loser")
    private UserDto loser;

    @JsonProperty("room")
    private RoomDto roomDto;

    @JsonProperty("type")
    private String type;

    @JsonProperty("reason")
    private String reason;

}
