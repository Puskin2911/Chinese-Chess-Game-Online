package com.doubleat.ccgame.exception;

import lombok.Getter;

@Getter
public class RoomIsFullPlayersException extends RuntimeException {
    private String message;

    public RoomIsFullPlayersException(String message) {
        super(message);
    }
}
