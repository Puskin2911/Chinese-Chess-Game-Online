package com.doubleat.ccgame.dto.converter;

import com.doubleat.ccgame.dto.common.UserDto;
import com.doubleat.ccgame.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User toEntity(UserDto userDto) {
        User entity = new User();
        //TODO convert
        return entity;
    }

    public UserDto toDto(User entity) {
        UserDto dto = new UserDto(entity.getId(), entity.getUsername(), entity.getElo());

        // TODO convert

        return dto;
    }
}
