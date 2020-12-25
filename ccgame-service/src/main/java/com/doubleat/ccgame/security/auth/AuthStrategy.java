package com.doubleat.ccgame.security.auth;

import com.doubleat.ccgame.dto.response.UserDto;
import com.doubleat.ccgame.dto.request.LoginRequest;
import com.doubleat.ccgame.dto.request.SignupRequest;
import com.doubleat.ccgame.exception.UsernameOrEmailHasAlreadyExistsException;
import org.springframework.security.authentication.BadCredentialsException;

public interface AuthStrategy {
    /**
     * Authenticate user and return access token.
     *
     * @param loginRequest the object contain username and password for authentication.
     * @return access token if username and password is valid.
     * @throws BadCredentialsException if username or password incorrect.
     */
    String authenticateUser(LoginRequest loginRequest);

    /**
     * Validate access token.
     *
     * @param accessToken the token need to validate.
     * @return {@code UserDto} if token is valid. Other wise, return {@code null}.
     */
    UserDto validateAccessToken(String accessToken);

    /**
     * Signup new user.
     *
     * @param signupRequest the object contains information for authentication.
     * @throws UsernameOrEmailHasAlreadyExistsException if username or email has already exists.
     */
    void signupUser(SignupRequest signupRequest);
}
