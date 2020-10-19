package com.doubleat.ccgame.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsernameOrEmailHasAlreadyExists extends RuntimeException {
    private String message;

    public UsernameOrEmailHasAlreadyExists(String message){
        this.message = message;
    }
}
