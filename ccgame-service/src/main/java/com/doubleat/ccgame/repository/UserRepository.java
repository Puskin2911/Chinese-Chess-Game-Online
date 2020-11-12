package com.doubleat.ccgame.repository;

import com.doubleat.ccgame.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    boolean existsUserByUsernameOrEmail(String username, String email);
}
