package com.doubleat.ccgame.exception;

import lombok.Getter;

@Getter
public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException(String message) {
        super(message);
    }
}
