package com.doubleat.ccgame.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GameCache {

    @NonNull
    private String winnerUsername;

    @NonNull
    private String loserUsername;

}
