package com.runningapi.runningapi.mapper;

import com.runningapi.runningapi.dto.response.user.UserResponseDto;
import com.runningapi.runningapi.model.User;
import com.runningapi.runningapi.model.enums.Provider;

public interface UserMapper {
    UserResponseDto toResponseDto(Long id, String name, String email, Provider provider);

    UserResponseDto toResponseDto(User user);
}
