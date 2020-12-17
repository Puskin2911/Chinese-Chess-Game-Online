package com.doubleat.ccgame.dto.converter;

import com.doubleat.ccgame.dto.common.UserDto;
import com.doubleat.ccgame.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User toEntity(UserDto userDto) {
        User entity = new User();
        entity.setUsername(userDto.getUsername());
        entity.setElo(userDto.getElo());

        return entity;
    }

    public UserDto toDto(User entity) {
        return UserDto.builder()
                .username(entity.getUsername())
                .elo(entity.getElo())
                .numberOfWins(entity.getGamesWin().size())
                .numberOfLoses(entity.getGamesLose().size())
                .build();
    }

}
