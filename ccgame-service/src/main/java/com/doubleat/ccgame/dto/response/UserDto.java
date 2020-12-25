package com.doubleat.ccgame.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserDto {
    @NonNull
    private String username;
    @NonNull
    private int elo;
    private int numberOfWins;
    private int numberOfLoses;
    private boolean ready;

}
