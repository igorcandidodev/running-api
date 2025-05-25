package com.runningapi.runningapi.mapper;

import com.runningapi.runningapi.dto.response.user.UserResponseDto;

public interface UserMapper {
    UserResponseDto toResponseDto(Long id, String name, String email, String provider);
}
