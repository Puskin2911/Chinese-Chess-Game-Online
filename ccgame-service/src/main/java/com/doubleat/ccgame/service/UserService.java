package com.doubleat.ccgame.service;

import com.doubleat.ccgame.dto.request.SignupRequest;
import com.doubleat.ccgame.entity.User;

public interface UserService {

    boolean addNew(SignupRequest signupRequest);

    User getByUsername(String username);
}
