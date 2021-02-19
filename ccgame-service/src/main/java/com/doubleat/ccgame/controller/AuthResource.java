package com.doubleat.ccgame.controller;

import com.doubleat.ccgame.dto.request.LoginRequest;
import com.doubleat.ccgame.dto.request.SignupRequest;
import com.doubleat.ccgame.dto.response.UserDto;
import com.doubleat.ccgame.security.SecurityUtils;
import com.doubleat.ccgame.security.auth.AuthStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthResource implements AuthController {

    private final AuthStrategy authStrategy;

    @Autowired
    public AuthResource(AuthStrategy authStrategy) {
        this.authStrategy = authStrategy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        String token = authStrategy.authenticateUser(loginRequest);

        HttpCookie cookie = SecurityUtils.createAuthCookie(token, 604800L);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<UserDto> validateAccessToken(@CookieValue(name = "access_token", required = false) String accessToken) {
        UserDto userDto = authStrategy.validateAccessToken(accessToken);

        if (userDto != null)
            return ResponseEntity.ok(userDto);
        else
            return ResponseEntity.badRequest().build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
        authStrategy.signupUser(signupRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
