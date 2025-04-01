package com.runningapi.runningapi.queue.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String exchangeName, String routingKey, Object message) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }
}
