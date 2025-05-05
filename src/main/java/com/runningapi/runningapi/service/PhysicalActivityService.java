package com.runningapi.runningapi.service;

import com.runningapi.runningapi.dto.PhysicalActivityDto;
import com.runningapi.runningapi.exceptions.UserNotFound;
import com.runningapi.runningapi.model.PhysicalActivity;
import com.runningapi.runningapi.model.User;
import com.runningapi.runningapi.repository.PhysicalActivityRepository;
import com.runningapi.runningapi.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PhysicalActivityService {

    private final PhysicalActivityRepository physicalActivityRepository;
    private final UserRepository userRepository;

    public PhysicalActivityService(PhysicalActivityRepository physicalActivityRepository,
                                   UserRepository userRepository) {
        this.physicalActivityRepository = physicalActivityRepository;
        this.userRepository = userRepository;
    }

    public PhysicalActivity createPhysicalActivity(@Valid PhysicalActivityDto physicalActivityDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername()); //email
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            var newPhysicalActivity = new PhysicalActivity(physicalActivityDto, user);
            return physicalActivityRepository.save(newPhysicalActivity);
        } else {
            throw new UserNotFound(userDetails.getUsername());
        }
    }
}
