package com.doubleat.ccgame.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
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

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "elo", nullable = false)
    private Integer elo;

    @OneToMany(mappedBy = "winner", fetch = FetchType.EAGER)
    private Set<Game> gamesWin;

    @OneToMany(mappedBy = "loser", fetch = FetchType.EAGER)
    private Set<Game> gamesLose;

}
