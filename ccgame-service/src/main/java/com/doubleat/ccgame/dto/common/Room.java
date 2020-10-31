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
    private Player redPlayer;
    private Player blackPlayer;
    private List<Player> viewers;
    private String boardStatus;
}
