package com.doubleat.ccgame.dto.common;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Room {
    @NonNull
    private int id;
    private UserDto redUserDto;
    private UserDto blackUserDto;
    private List<UserDto> viewers;
    private String boardStatus;
}
