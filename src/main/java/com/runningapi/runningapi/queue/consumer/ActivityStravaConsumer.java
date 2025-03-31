package com.runningapi.runningapi.queue.consumer;

import com.runningapi.runningapi.dto.strava.request.WebhookEvent;
import com.runningapi.runningapi.service.strava.StravaServices;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivityStravaConsumer {

    @Autowired
    private StravaServices stravaServices;

    @RabbitListener(queues = "${queue.activity.update.strava}")
    public void consumeActivityStravaCreated(WebhookEvent webhookEvent) {

        stravaServices.processWebhookActivityCreated(webhookEvent);
    }
}
