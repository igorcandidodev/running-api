package com.runningapi.runningapi.queue.services;

import com.runningapi.runningapi.queue.producer.QueueSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailQueueService {

    @Value("${exchange.email.service}")
    private String exchangeEmailService;

    @Value("${key.email.user.registration}")
    private String keyEmailUserRegistration;

    @Autowired
    private QueueSender queueSender;

    public void sendEmailUserRegistration(Object message) {
        queueSender.sendMessage(exchangeEmailService, keyEmailUserRegistration, message);
    }
}
