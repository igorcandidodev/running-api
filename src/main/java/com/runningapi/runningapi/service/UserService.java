package com.runningapi.runningapi.service;

import com.runningapi.runningapi.dto.request.user.UserRequestDto;
import com.runningapi.runningapi.exceptions.UserException;
import com.runningapi.runningapi.model.User;
import com.runningapi.runningapi.repository.UserRepository;
import com.runningapi.runningapi.utils.BCryptPasswordUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        user.setProvider("LOCAL");

        return userRepository.save(user);
    }
}
