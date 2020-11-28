package com.doubleat.ccgame.controller;

import com.doubleat.ccgame.dto.request.LoginRequest;
import com.doubleat.ccgame.dto.request.SignupRequest;
import com.doubleat.ccgame.security.SecurityUtils;
import com.doubleat.ccgame.security.auth.AuthStrategy;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth")
@Api(tags = "Authentication")
public class AuthController {

    private final AuthStrategy authStrategy;

    @Autowired
    public AuthController(AuthStrategy authStrategy) {
        this.authStrategy = authStrategy;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        String token = authStrategy.authenticateUser(loginRequest);

        HttpCookie cookie = SecurityUtils.createAuthCookie(token, 604800L);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }

    @GetMapping(value = "/validate")
    public ResponseEntity<?> validateAccessToken(@CookieValue(name = "access_token", required = false) String accessToken) {
        boolean isValid = authStrategy.validateAccessToken(accessToken);

        if (isValid) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
        authStrategy.signupUser(signupRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
