package com.doubleat.ccgame.service;

import com.doubleat.ccgame.dto.response.UserDto;
import com.doubleat.ccgame.dto.request.SignupRequest;
import com.doubleat.ccgame.domain.User;

public interface UserService {

    boolean addNew(SignupRequest signupRequest);

    UserDto getDtoByUsername(String username);

    User getByUsername(String username);

}
