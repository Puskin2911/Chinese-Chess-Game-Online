package com.doubleat.ccgame.controller;

import com.doubleat.ccgame.dto.request.LoginRequest;
import com.doubleat.ccgame.dto.request.SignupRequest;
import com.doubleat.ccgame.dto.response.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Auth Resources", tags = "Authentication")
@RequestMapping(value = AuthController.ROOT_API)
public interface AuthController extends BaseController {

    String AUTH_RESOURCE = "auth";
    String ROOT_API = PUBLIC_API + VERSION_V1 + AUTH_RESOURCE;

    String LOGIN_RESOURCE = "/login";
    String VALIDATE_RESOURCE = "/validate";
    String SIGNUP_RESOURCE = "/signup";

    @ApiOperation(value = "Authenticate user with username and password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Authenticate user successfully"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping(value = LOGIN_RESOURCE)
    ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest);

    @ApiOperation(value = "Validate user's access token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Validate access token successfully"),
            @ApiResponse(code = 400, message = "Invalid access token")
    })
    @GetMapping(value = VALIDATE_RESOURCE)
    ResponseEntity<UserDto> validateAccessToken(@CookieValue(name = "access_token", required = false) String accessToken);

    @ApiOperation(value = "Sign up one user to system")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created successfully"),
            @ApiResponse(code = 409, message = "User has already exists")
    })
    @PostMapping(value = SIGNUP_RESOURCE)
    ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest);

}
