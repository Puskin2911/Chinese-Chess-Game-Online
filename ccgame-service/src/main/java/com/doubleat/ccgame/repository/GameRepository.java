package com.doubleat.ccgame.repository;

import com.doubleat.ccgame.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {
}
