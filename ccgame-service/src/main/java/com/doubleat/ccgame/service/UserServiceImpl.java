package com.doubleat.ccgame.service;

import com.doubleat.ccgame.domain.User;
import com.doubleat.ccgame.dto.converter.UserConverter;
import com.doubleat.ccgame.dto.request.SignupRequest;
import com.doubleat.ccgame.dto.response.UserDto;
import com.doubleat.ccgame.game.GameConstants;
import com.doubleat.ccgame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean addNew(SignupRequest signupRequest) {
        String username = signupRequest.getUsername();
        String email = signupRequest.getEmail();
        if (userRepository.existsUserByUsernameOrEmail(username, email))
            return false;
        User user = User.builder()
                .username(username)
                .email(email)
                .passHashed(passwordEncoder.encode(signupRequest.getPassword()))
                .elo(GameConstants.DEFAULT_ELO)
                .build();

        userRepository.save(user);

        return true;
    }

    @Override
    public UserDto getDtoByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(username, username);
        return userOptional.map(user -> userConverter.toDto(user)).orElse(null);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

}
