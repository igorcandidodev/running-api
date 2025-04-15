package com.runningapi.runningapi.queue.consumer;

import com.runningapi.runningapi.dto.strava.request.WebhookEvent;
import com.runningapi.runningapi.exceptions.StravaAthleteNotFoundAsyncException;
import com.runningapi.runningapi.exceptions.StravaAuthenticationAsyncException;
import com.runningapi.runningapi.service.strava.StravaServices;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivityStravaConsumer {

    @Autowired
    private StravaServices stravaServices;

    @RabbitListener(queues = "${queue.activity.created.strava}")
    public void consumeActivityStravaCreated(WebhookEvent webhookEvent) {
        try {
            stravaServices.processWebhookActivityCreated(webhookEvent);
        } catch (StravaAthleteNotFoundAsyncException | StravaAuthenticationAsyncException e) {
            // Logic to try again to get the activity and link it to the user

        } catch (Exception e) {
            // Handle other exceptions
            System.out.println("Error processing activity: " + e.getMessage());
        }
    }

    @RabbitListener(queues = "${queue.activity.updated.strava}")
    public void consumeActivityStravaUpdated(WebhookEvent webhookEvent) {
        try {
            stravaServices.processWebhookActivityUpdated(webhookEvent);
        } catch (StravaAthleteNotFoundAsyncException | StravaAuthenticationAsyncException e) {
            // Logic to try again to get the activity and link it to the user

        } catch (Exception e) {
            // Handle other exceptions
            System.out.println("Error processing activity: " + e.getMessage());
        }
    }

    @RabbitListener(queues = "${queue.activity.deleted.strava}")
    public void consumeActivityStravaDeleted(WebhookEvent webhookEvent) {
        try {
            stravaServices.processWebhookActivityDeleted(webhookEvent);
        } catch (StravaAthleteNotFoundAsyncException | StravaAuthenticationAsyncException e) {
            // Logic to try again to get the activity and link it to the user

        } catch (Exception e) {
            // Handle other exceptions
            System.out.println("Error processing activity: " + e.getMessage());
        }
    }
}
