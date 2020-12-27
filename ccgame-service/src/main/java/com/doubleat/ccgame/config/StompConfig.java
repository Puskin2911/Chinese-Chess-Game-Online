package com.doubleat.ccgame.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Getter
public class StompConfig {

    @Autowired
    private Environment environment;

    private int disconnectDelay;

    private int httpMessageCacheSize;

    private int streamBytesLimit;

    @PostConstruct
    public void init() {
        this.disconnectDelay = environment
                .getProperty("ccgame.stomp.disconnectDelay", Integer.class, 900);

        this.httpMessageCacheSize = environment
                .getProperty("ccgame.stomp.httpMessageCacheSize", Integer.class, 1000);

        this.streamBytesLimit = environment
                .getProperty("ccgame.stomp.streamBytesLimit", Integer.class, 524288);
    }

}
