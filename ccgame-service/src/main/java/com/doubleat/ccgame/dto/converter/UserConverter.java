package com.doubleat.ccgame.dto.converter;

import com.doubleat.ccgame.domain.User;
import com.doubleat.ccgame.dto.common.UserDto;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

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
                .numberOfWins(Optional.of(entity.getGamesWin()).map(Set::size).orElse(0))
                .numberOfLoses(Optional.of(entity.getGamesLose()).map(Set::size).orElse(0))
                .build();
    }

}
