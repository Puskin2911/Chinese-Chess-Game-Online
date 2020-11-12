package com.doubleat.ccgame.jwt;

import org.springframework.security.core.Authentication;

public interface JwtService {

    String generateJwtToken(Authentication authentication);

    String getUsernameFromJwtToken(String token);

    boolean validateJwtToken(String token);
}
