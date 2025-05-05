package com.runningapi.runningapi.service;

import com.runningapi.runningapi.dto.RunningActivityDto;
import com.runningapi.runningapi.exceptions.UserNotFound;
import com.runningapi.runningapi.model.RunningActivity;
import com.runningapi.runningapi.model.User;
import com.runningapi.runningapi.repository.RunningActivityRepository;
import com.runningapi.runningapi.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RunningActivityService {

    private final RunningActivityRepository runningActivityRepository;
    private final UserRepository userRepository;

    public RunningActivityService(RunningActivityRepository runningActivityRepository, UserRepository userRepository) {
        this.runningActivityRepository = runningActivityRepository;
        this.userRepository = userRepository;
    }

    public RunningActivity createRunningActivity(@Valid RunningActivityDto runningActivityDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            var newRunningActivity = new RunningActivity(runningActivityDto, user);
            return runningActivityRepository.save(newRunningActivity);
        } else {
            throw new UserNotFound(userDetails.getUsername());
        }
    }
}