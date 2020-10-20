package com.doubleat.ccgame.model;

import com.doubleat.ccgame.dto.common.UserDto;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Room {
    @NonNull
    private Integer id;
    private UserDto redUser;
    private UserDto blackUser;
    private List<UserDto> watchingUsers;
    private String boardStatus;
}
