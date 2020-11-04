package com.doubleat.ccgame.exception;

import lombok.Getter;

@Getter
public class RoomNotFoundException extends RuntimeException {
    private String message;

    public RoomNotFoundException(String message) {
        super(message);
    }
}
