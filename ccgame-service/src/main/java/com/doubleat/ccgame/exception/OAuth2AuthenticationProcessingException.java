package com.doubleat.ccgame.exception;

import org.springframework.security.core.AuthenticationException;

public class OAuth2AuthenticationProcessingException extends AuthenticationException {
    public OAuth2AuthenticationProcessingException(String messages, Throwable t) {
        super(messages, t);
    }

    public OAuth2AuthenticationProcessingException(String messages) {
        super(messages);
    }
}
