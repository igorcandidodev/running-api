package com.runningapi.runningapi.controllers;

import com.runningapi.runningapi.dto.request.user.UserRequestDto;
import com.runningapi.runningapi.dto.response.user.UserResponseDto;
import com.runningapi.runningapi.mapper.UserMapper;
import com.runningapi.runningapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/")
    public String home() {
        return "Home";
    }

    @PostMapping("/")
    @Transactional
    @Operation(summary = "Create User")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        var user = userService.createUser(userRequestDto);

        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        var userDto = userMapper.toResponseDto(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getProvider()
        );

        return ResponseEntity.created(uri).body(userDto);
    }

}
