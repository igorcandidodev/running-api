package com.runningapi.runningapi.queue.configuration;

import com.runningapi.runningapi.configuration.QueueActivityStravaConfig;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class ActivityStravaQueueConfig {

    @Autowired
    private QueueActivityStravaConfig queueActivityStravaConfig;

    @Bean
    public DirectExchange activityStravaExchange() {
        return new DirectExchange(queueActivityStravaConfig.getExchangeActivityStrava(), true, false);
    }

    @Bean
    public DirectExchange activityStravaRetryExchange() {
        return new DirectExchange(queueActivityStravaConfig.getExchangeActivityStravaRetry(), true, false);
    }

    @Bean
    public Declarables activityStravaQueues() {
        Map<String, String> keysQueues = Map.of(
                queueActivityStravaConfig.getKeyActivityCreatedStrava(), queueActivityStravaConfig.getQueueActivityCreatedStrava(),
                queueActivityStravaConfig.getKeyActivityUpdatedStrava(), queueActivityStravaConfig.getQueueActivityUpdatedStrava(),
                queueActivityStravaConfig.getKeyActivityDeletedStrava(), queueActivityStravaConfig.getQueueActivityDeletedStrava()
        );

        List<Declarable> declarables = new ArrayList<>();
        keysQueues.forEach((key, queueName)  -> {
            var queueRetry = queueName + ".retry";
            var keyRetry = key + ".retry";
            var queueDlq = queueName + ".dlq";
            var keyDlq = key + ".dlq";

            // Main queue config
            Queue mainQueue = QueueBuilder.durable(queueName)
                    .deadLetterExchange(queueActivityStravaConfig.getExchangeActivityStrava())
                    .deadLetterRoutingKey(keyRetry)
                    .build();

            Binding mainBinding = BindingBuilder.bind(mainQueue)
                    .to(activityStravaExchange())
                    .with(key);

            // Retry queue config
            Queue retryQueue = QueueBuilder.durable(queueRetry)
                    .deadLetterExchange(queueActivityStravaConfig.getExchangeActivityStrava())
                    .deadLetterRoutingKey(key)
                    .ttl(queueActivityStravaConfig.getRetryDelayInMs())
                    .build();

            Binding retryBinding = BindingBuilder.bind(retryQueue)
                    .to(activityStravaExchange())
                    .with(keyRetry);

            // DLQ config
            Queue dlqQueue = QueueBuilder
                    .durable(queueDlq)
                    .build();

            Binding dlqBinding = BindingBuilder.bind(dlqQueue)
                    .to(activityStravaExchange())
                    .with(keyDlq);

            declarables.addAll(List.of(
                    mainQueue, mainBinding,
                    retryQueue, retryBinding,
                    dlqQueue, dlqBinding
            ));
        });
        return new Declarables(declarables);
    }
}
