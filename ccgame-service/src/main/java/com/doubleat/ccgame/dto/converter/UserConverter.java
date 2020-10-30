package com.doubleat.ccgame.dto.converter;

import com.doubleat.ccgame.dto.common.Player;
import com.doubleat.ccgame.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User toEntity(Player player) {
        User entity = new User();
        //TODO convert
        return entity;
    }

    public Player toDto(User entity) {
        Player dto = new Player(entity.getId(), entity.getUsername(), entity.getElo());

        // TODO convert

        return dto;
    }
}
