package com.doubleat.ccgame.game.exception;

import lombok.Getter;

@Getter
public class InvalidMoveException extends RuntimeException {
    private String message;

    public InvalidMoveException(String message) {
        super(message);
    }
}
