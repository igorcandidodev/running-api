package com.runningapi.runningapi.queue.consumer;

import com.runningapi.runningapi.model.User;
import com.runningapi.runningapi.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "${queue.email.user.registration}")
    public void sendEmailUserRegistration(User user) {
        emailService.sendEmailRegistration(user);
    }
}
