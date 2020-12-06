package com.doubleat.ccgame.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageResponse<T> {
    private T data;
    private MessageResponseType type;

    public enum MessageResponseType {
        CHAT,
        MOVE,
        READY,
        UNDO_READY
    }
}
