package com.doubleat.ccgame.controller;

import com.doubleat.ccgame.dto.converter.UserConverter;
import com.doubleat.ccgame.entity.User;
import com.doubleat.ccgame.room.Room;
import com.doubleat.ccgame.room.RoomStrategy;
import com.doubleat.ccgame.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/rooms/")
@Api(tags = {"room service"})
public class RoomController {
    @Autowired
    private RoomStrategy roomStrategy;
    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;

    @PostMapping(value = "/join")
    public ResponseEntity<Room> joinRoom(Authentication authentication) {
        User user = userService.getByUsername(authentication.getName());
        Room room = roomStrategy.playerJoinRoom(userConverter.toDto(user));

        return ResponseEntity.ok(room);
    }

    @PostMapping(value = "/{roomId}/leave")
    public ResponseEntity<Boolean> leaveRoom(@PathVariable int roomId, Authentication authentication) {
        User user = userService.getByUsername(authentication.getName());
        Boolean success = roomStrategy.playerLeaveRoom(userConverter.toDto(user), roomId);

        return ResponseEntity.ok(success);
    }

    @PostMapping(value = "/{roomId}/join")
    public ResponseEntity<Room> joinSpecificRoom(@PathVariable int roomId, Authentication authentication) {
        User user = userService.getByUsername(authentication.getName());
        Room room = roomStrategy.playerJoinRoom(userConverter.toDto(user), roomId);

        return ResponseEntity.ok(room);
    }
}
