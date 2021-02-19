package com.doubleat.ccgame.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {

    private Date timestamp;

    private String message;

    private String details;

}
