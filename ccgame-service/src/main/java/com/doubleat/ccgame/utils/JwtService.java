package com.doubleat.ccgame.utils;

import org.springframework.security.core.Authentication;

public interface JwtService {

    String generateJwtToken(Authentication authentication);

    String getUsernameFromJwtToken(String token);

    void validateJwtToken(String token);
}
