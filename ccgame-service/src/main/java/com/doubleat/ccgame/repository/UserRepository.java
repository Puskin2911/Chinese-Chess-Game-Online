package com.doubleat.ccgame.repository;

import com.doubleat.ccgame.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

    boolean existsUserByUsernameOrEmail(String username, String email);

    Optional<User> findByEmail(String email);

}
