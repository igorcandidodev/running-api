package com.runningapi.runningapi.service;

import com.runningapi.runningapi.dto.ObjectiveDto;
import com.runningapi.runningapi.exceptions.UserNotFound;
import com.runningapi.runningapi.model.Objective;
import com.runningapi.runningapi.model.User;
import com.runningapi.runningapi.repository.ObjectiveRepository;
import com.runningapi.runningapi.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ObjectiveService {

    private final ObjectiveRepository objectiveRepository;
    private final UserRepository userRepository;

    public ObjectiveService(ObjectiveRepository objectiveRepository, UserRepository userRepository) {
        this.objectiveRepository = objectiveRepository;
        this.userRepository = userRepository;
    }

    public Objective createObjective(@Valid ObjectiveDto objectiveDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            var newObjective = new Objective(objectiveDto, user);
            return objectiveRepository.save(newObjective);
        } else {
            throw new UserNotFound(userDetails.getUsername());
        }
    }
}