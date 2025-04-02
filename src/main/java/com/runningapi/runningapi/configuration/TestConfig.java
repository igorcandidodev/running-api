package com.runningapi.runningapi.configuration;

import com.runningapi.runningapi.model.User;
import com.runningapi.runningapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
//        var user = new User();
//        user.setEmail("runai@gmail.com");
//        user.setFullName("Run AI");
//        user.setPassword("123456");
//        user.setBirthDate(LocalDate.of(2000, 1, 1));
//
//        userRepository.save(user);
    }
}
