package com.doubleat.ccgame.dto.common;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDto {
    @NonNull
    private int id;
    @NonNull
    private String username;
    @NonNull
    private int elo;
    private int numberOfWins;
    private int numberOfLoses;
}
