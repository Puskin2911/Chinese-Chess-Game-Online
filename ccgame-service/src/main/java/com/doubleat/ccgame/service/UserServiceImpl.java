package com.doubleat.ccgame.service;

import com.doubleat.ccgame.dto.request.SignupRequest;
import com.doubleat.ccgame.entity.User;
import com.doubleat.ccgame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${games.elo}")
    private Integer elo;

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
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassHashed(passwordEncoder.encode(signupRequest.getPassword()));
        user.setElo(elo);

        userRepository.save(user);

        return true;
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
