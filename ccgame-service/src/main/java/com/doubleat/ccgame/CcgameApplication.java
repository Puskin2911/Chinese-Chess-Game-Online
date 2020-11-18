package com.doubleat.ccgame;

import com.doubleat.ccgame.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class CcgameApplication {

    public static void main(String[] args) {
        SpringApplication.run(CcgameApplication.class, args);
    }

}
