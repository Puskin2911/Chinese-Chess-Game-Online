package com.doubleat.ccgame.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final Auth auth = new Auth();
    private final OAuth2 oAuth2 = new OAuth2();
    private final Game game = new Game();

    @Getter
    @Setter
    public static class Auth {
        private String tokenSecret;
        private long tokenExpirationMs;
    }

    @Getter
    @Setter
    public static class OAuth2 {
        private final List<String> authorizedRedirectUris = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class Game {
        private Integer elo;
    }
}
