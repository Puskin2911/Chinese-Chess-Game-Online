package com.doubleat.ccgame.security.auth;

import com.doubleat.ccgame.dto.request.LoginRequest;
import com.doubleat.ccgame.dto.request.SignupRequest;
import com.doubleat.ccgame.exception.UsernameOrEmailHasAlreadyExistsException;
import com.doubleat.ccgame.security.jwt.JwtService;
import com.doubleat.ccgame.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthStrategyImpl implements AuthStrategy {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserService userService;

    public AuthStrategyImpl(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }


    @Override
    public String authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtService.generateJwtToken(authentication);
    }

    @Override
    public boolean validateAccessToken(String accessToken) {
        return jwtService.validateJwtToken(accessToken);
    }

    @Override
    public void signupUser(SignupRequest signupRequest) {
        boolean res = userService.addNew(signupRequest);

        if (!res) throw new UsernameOrEmailHasAlreadyExistsException("Username or email has already exists!");
    }
}
