package com.doubleat.ccgame.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "pass_hashed")
    private String passHashed;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "elo", nullable = false)
    private Integer elo;

    @OneToMany(mappedBy = "winner", fetch = FetchType.EAGER)
    private Set<Game> gamesWin;

    @OneToMany(mappedBy = "loser", fetch = FetchType.EAGER)
    private Set<Game> gamesLose;

}
