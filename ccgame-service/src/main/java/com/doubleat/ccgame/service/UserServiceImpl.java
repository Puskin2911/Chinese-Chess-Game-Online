package com.doubleat.ccgame.service;

import com.doubleat.ccgame.config.AppProperties;
import com.doubleat.ccgame.domain.User;
import com.doubleat.ccgame.dto.common.UserDto;
import com.doubleat.ccgame.dto.converter.UserConverter;
import com.doubleat.ccgame.dto.request.SignupRequest;
import com.doubleat.ccgame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private int elo;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        this.elo = appProperties.getGame().getElo();
    }

    @Override
    public boolean addNew(SignupRequest signupRequest) {
        String username = signupRequest.getUsername();
        String email = signupRequest.getEmail();
        if (userRepository.existsUserByUsernameOrEmail(username, email))
            return false;
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassHashed(passwordEncoder.encode(signupRequest.getPassword()));
        user.setElo(elo);

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
