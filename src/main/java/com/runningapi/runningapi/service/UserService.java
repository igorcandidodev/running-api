package com.runningapi.runningapi.service;

import com.runningapi.runningapi.dto.request.user.UserRequestDto;
import com.runningapi.runningapi.dto.response.user.UserResponseDto;
import com.runningapi.runningapi.exceptions.UserException;
import com.runningapi.runningapi.mapper.UserMapper;
import com.runningapi.runningapi.model.User;
import com.runningapi.runningapi.model.enums.Provider;
import com.runningapi.runningapi.repository.UserRepository;
import com.runningapi.runningapi.utils.BCryptPasswordUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDto getUserAuthenticated() {
        var userAuthenticated = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var user = userRepository.findByEmail(userAuthenticated.getUsername())
                .orElseThrow(() -> new UserException("Usuário não encontrado: " + userAuthenticated.getUsername()));

        return userMapper.toResponseDto(user);
    }

    public User createUser(UserRequestDto userDto) {
        var userExist = userRepository.findByEmail(userDto.email());

        if (userExist.isPresent()) {
            throw new UserException("Usuário com email já cadastrado: " + userDto.email());
        }
        var user = new User();
        user.setFullName(userDto.name());
        user.setEmail(userDto.email());
        user.setPassword(BCryptPasswordUtil.hashPassword(userDto.password()));
        user.setProvider(Provider.LOCAL);

        return userRepository.save(user);
    }
}
