package com.doubleat.ccgame.dto.converter;

import com.doubleat.ccgame.dto.response.UserDto;
import com.doubleat.ccgame.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User toEntity(UserDto userDto) {
        User entity = User.builder()
                .username(userDto.getUsername())
                .elo(userDto.getElo())
                .build();

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
