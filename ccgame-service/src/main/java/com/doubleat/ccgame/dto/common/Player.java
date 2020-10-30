package com.doubleat.ccgame.dto.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Player {
    @NonNull
    private int id;
    @NonNull
    private String username;
    @NonNull
    private int elo;
    private int numberOfWins;
    private int numberOfLoses;
    private boolean ready;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player)
            return this.username == ((Player) obj).getUsername();

        return false;
    }
}
