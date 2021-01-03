package com.doubleat.ccgame.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DrawResponse {

    @JsonProperty("username")
    private String username;

    @JsonProperty("isAgree")
    private Boolean isAgree;

}
