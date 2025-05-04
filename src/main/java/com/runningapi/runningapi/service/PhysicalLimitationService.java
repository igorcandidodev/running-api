package com.runningapi.runningapi.service;

import com.runningapi.runningapi.dto.PhysicalLimitationDto;
import com.runningapi.runningapi.exceptions.UserNotFound;
import com.runningapi.runningapi.model.PhysicalLimitation;
import com.runningapi.runningapi.model.User;
import com.runningapi.runningapi.repository.PhysicalLimitationRepository;
import com.runningapi.runningapi.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PhysicalLimitationService {

    private final PhysicalLimitationRepository physicalLimitationRepository;
    private final UserRepository userRepository;

    public PhysicalLimitationService(PhysicalLimitationRepository physicalLimitationRepository,
                                     UserRepository userRepository) {
        this.physicalLimitationRepository = physicalLimitationRepository;
        this.userRepository = userRepository;
    }

    public PhysicalLimitation createPhysicalLimitation(@Valid PhysicalLimitationDto physicalLimitationDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            var newPhysicalLimitation = new PhysicalLimitation(physicalLimitationDto, user);
            return physicalLimitationRepository.save(newPhysicalLimitation);
        } else {
            throw new UserNotFound(userDetails.getUsername());
        }
    }
}