package com.runningapi.runningapi.service;

import com.runningapi.runningapi.exceptions.InvalidResetException;
import com.runningapi.runningapi.exceptions.TokenInvalidException;
import com.runningapi.runningapi.exceptions.TooManyAttemptsException;
import com.runningapi.runningapi.model.PasswordReset;
import com.runningapi.runningapi.model.User;
import com.runningapi.runningapi.model.enums.Provider;
import com.runningapi.runningapi.repository.PasswordResetRepository;
import com.runningapi.runningapi.repository.UserRepository;
import com.runningapi.runningapi.utils.BCryptPasswordUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PasswordResetService {

    private static final int MAX_ATTEMPTS = 5;
    private final PasswordResetRepository passwordResetRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public PasswordResetService(PasswordResetRepository passwordResetRepository, UserRepository userRepository, EmailService emailService) {
        this.passwordResetRepository = passwordResetRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        if (!user.getProvider().equals(Provider.LOCAL)) {
            throw new InvalidResetException("Reset de senha não permitido.");
        }

        passwordResetRepository.findByUserEmail(email)
                .ifPresent(passwordResetRepository::delete);

        String code = PasswordReset.generateRandomCode();
        Instant expiration = Instant.now().plusSeconds(300);

        PasswordReset passwordReset = new PasswordReset(code, expiration, user);
        passwordResetRepository.save(passwordReset);

        emailService.sendEmailForgetPassword(code, user.getFullName(), user.getEmail());
    }

    public void resetPassword(String code, String newPassword) {
        PasswordReset passwordReset = passwordResetRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Código inválido."));

        if (passwordReset.isExpired()) {
            throw new TokenInvalidException("Código expirado.");
        }
        if (passwordReset.getAttempts() >= MAX_ATTEMPTS) {
            passwordResetRepository.delete(passwordReset);
            throw new TooManyAttemptsException("Muitas tentativas inválidas. Solicite um novo código.");
        }

        User user = passwordReset.getUser();
        user.setPassword(BCryptPasswordUtil.hashPassword(newPassword));
        userRepository.save(user);

        passwordResetRepository.delete(passwordReset);
    }

    @Scheduled(cron = "0 */24 * * *")
    public void cleanExpiredResets() {
        passwordResetRepository.findAll().stream()
                .filter(PasswordReset::isExpired)
                .forEach(passwordResetRepository::delete);
    }
}