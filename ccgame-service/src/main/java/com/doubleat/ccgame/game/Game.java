package com.doubleat.ccgame.game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game {
    private Player redPlayer;
    private Player blackPlayer;
    private Board board;
    private boolean redTurn;
    
}
