package com.doubleat.ccgame.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Getter
@Setter
public class AuthConfig {

    @Autowired
    private Environment environment;

    private String tokenSecret;

    private long tokenExpirationMs;

    @PostConstruct
    public void init() {
        this.tokenSecret = environment.getProperty("ccgame.auth.tokenSecret", "ccgameTokenSecret");

        this.tokenExpirationMs = environment
                .getProperty("ccgame.auth.tokenExpirationMs", Long.class, 86400000L);
    }

}
