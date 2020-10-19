package com.doubleat.ccgame.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ResponseError {
    private Date timestamp;
    private String message;
    private String details;
}
