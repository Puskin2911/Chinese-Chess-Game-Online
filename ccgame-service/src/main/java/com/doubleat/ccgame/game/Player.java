package com.doubleat.ccgame.game;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Data
public class Player {

    @NonNull
    private String username;

    @NonNull
    private boolean isRed;

}
