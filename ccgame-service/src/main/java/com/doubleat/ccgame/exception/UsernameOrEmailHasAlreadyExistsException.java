package com.doubleat.ccgame.exception;

import lombok.Getter;

@Getter
public class UsernameOrEmailHasAlreadyExistsException extends RuntimeException {

    public UsernameOrEmailHasAlreadyExistsException(String message) {
        super(message);
    }

}
