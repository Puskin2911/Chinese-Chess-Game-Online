package com.doubleat.ccgame.service;

import com.doubleat.ccgame.dto.request.SignupRequest;

public interface UserService {

    boolean addNew(SignupRequest signupRequest);
}
