package com.doubleat.ccgame.dto.converter;

import com.doubleat.ccgame.dto.response.RoomDto;
import com.doubleat.ccgame.room.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomConverter {
    public RoomDto toDto(Room entity) {
        return RoomDto.builder()
                .id(entity.getId())
                .players(entity.getPlayers())
                .viewers(entity.getViewers())
                .build();
    }

}
