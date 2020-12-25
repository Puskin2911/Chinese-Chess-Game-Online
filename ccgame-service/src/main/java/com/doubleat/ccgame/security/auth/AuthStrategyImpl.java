package com.doubleat.ccgame.security.auth;

import com.doubleat.ccgame.domain.User;
import com.doubleat.ccgame.dto.response.UserDto;
import com.doubleat.ccgame.dto.request.LoginRequest;
import com.doubleat.ccgame.dto.request.SignupRequest;
import com.doubleat.ccgame.exception.UsernameOrEmailHasAlreadyExistsException;
import com.doubleat.ccgame.repository.UserRepository;
import com.doubleat.ccgame.security.jwt.JwtTokenProvider;
import com.doubleat.ccgame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthStrategyImpl implements AuthStrategy {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    private UserRepository userRepository;

    public AuthStrategyImpl(AuthenticationManager authenticationManager,
                            JwtTokenProvider jwtTokenProvider,
                            UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
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

        return jwtTokenProvider.generateJwtToken(authentication);
    }

    @Override
    public UserDto validateAccessToken(String accessToken) {
        boolean isValidToken = jwtTokenProvider.validateJwtToken(accessToken);
        if (isValidToken) {
            String username = jwtTokenProvider.getUsernameFromJwtToken(accessToken);
            Optional<User> userOptional = userRepository.findByUsername(username);

            if (userOptional.isEmpty()) {
                // TODO: Check user is null
                return null;
            }

            User user = userOptional.get();

            return UserDto.builder()
                    .username(user.getUsername())
                    .elo(user.getElo())
                    .numberOfLoses(user.getGamesLose().size())
                    .numberOfWins(user.getGamesWin().size())
                    .ready(false)
                    .build();
        }

        return null;
    }

    @Override
    public void signupUser(SignupRequest signupRequest) {
        boolean res = userService.addNew(signupRequest);

        if (!res)
            throw new UsernameOrEmailHasAlreadyExistsException("Username or email has already exists!");
    }

}
