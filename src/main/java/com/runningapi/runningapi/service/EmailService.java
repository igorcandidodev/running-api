package com.runningapi.runningapi.service;

import com.google.api.client.util.Value;
import com.runningapi.runningapi.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String from;

    @Value("${mail.from.name}")
    private String fromName;

    Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmailRegistration(User user) {

        try {
            final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            final MimeMessageHelper email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            email.setTo(user.getEmail());
            email.setFrom(new InternetAddress(from, fromName));
            email.setSubject("Bem-vindo(a) ao Moove");

            final Context ctx = new Context(LocaleContextHolder.getLocale());

            ctx.setVariable("name", user.getFullName());

            final String htmlContent = templateEngine.process("email-registration", ctx);

            email.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException | UnsupportedEncodingException e) {
            logger.error("Erro ao enviar email de boas vindas: e-mail: {}", user.getEmail(), e);
        }
    }

    public void sendEmailForgetPassword(String code, String name, String email) {
        try {
            final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            final MimeMessageHelper emailHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            emailHelper.setTo(email);
            emailHelper.setFrom(new InternetAddress(from, fromName));
            emailHelper.setSubject("Recuperação de Senha");

            final Context ctx = new Context(LocaleContextHolder.getLocale());

            ctx.setVariable("name", name);
            ctx.setVariable("code", code);

            final String htmlContent = templateEngine.process("email-forget-password", ctx);

            emailHelper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException | UnsupportedEncodingException e) {
            logger.error("Erro ao enviar email de recuperação de senha: e-mail: {}", email, e);
        }
    }
}
