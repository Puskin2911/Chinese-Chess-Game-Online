package com.doubleat.ccgame.security.jwt;

import org.springframework.security.core.Authentication;

public interface JwtTokenProvider {

    String generateJwtToken(Authentication authentication);

    String getUsernameFromJwtToken(String token);

    boolean validateJwtToken(String token);
}
