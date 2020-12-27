package com.doubleat.ccgame.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "ccgame.mvc")
public class MvcConfig {

    private List<String> allowedOrigins = new ArrayList<>();

}
