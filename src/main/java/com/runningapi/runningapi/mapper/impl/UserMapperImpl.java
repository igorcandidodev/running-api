package com.runningapi.runningapi.mapper.impl;

import com.runningapi.runningapi.dto.response.user.UserResponseDto;
import com.runningapi.runningapi.mapper.UserMapper;
import com.runningapi.runningapi.model.enums.Provider;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDto toResponseDto(Long id, String name, String email, Provider provider) {
        return new UserResponseDto(id, name, email, provider);
    }
}
