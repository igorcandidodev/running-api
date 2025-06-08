package com.runningapi.runningapi.queue.consumer;

import com.runningapi.runningapi.configuration.QueueActivityStravaConfig;
import com.runningapi.runningapi.dto.strava.request.WebhookEvent;
import com.runningapi.runningapi.exceptions.StravaAthleteNotFoundAsyncException;
import com.runningapi.runningapi.exceptions.StravaAuthenticationAsyncException;
import com.runningapi.runningapi.exceptions.StravaInternalServerErrorAsyncException;
import com.runningapi.runningapi.queue.producer.QueueSender;
import com.runningapi.runningapi.service.strava.StravaServices;
import com.runningapi.runningapi.utils.QueueUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.logging.Logger;

@Component
public class ActivityStravaConsumer {

    private static final Logger logger = Logger.getLogger(ActivityStravaConsumer.class.getName());

    @Autowired
    private StravaServices stravaServices;

    @Autowired
    private QueueSender queueSender;

    @Autowired
    private QueueActivityStravaConfig queueConfig;

    @RabbitListener(queues = "${queue.activity.created.strava}")
    public void consumeActivityStravaCreated(WebhookEvent webhookEvent,
                                             @Header(required = false, name = "x-death") Map<String, ?> xDeath) {
        try {
            stravaServices.processWebhookActivityCreated(webhookEvent);
        } catch (StravaAuthenticationAsyncException e) {
            logger.warning("Authentication error while processing activity, probably the user has not linked their Strava account or token expired\n"
                    + "Object ID: " + webhookEvent.objectId() + ", Owner ID: " + webhookEvent.ownerId() + "\n"
                    + "Aspect Type: " + webhookEvent.aspectType() + ", Event Time: " + webhookEvent.eventTime() + "\n"
                    + "Error Message: " + e.getMessage()
            );

            queueSender.sendMessage(
                    queueConfig.getExchangeActivityStrava(),
                    queueConfig.getKeyActivityCreatedStravaDlq(),
                    webhookEvent
            );

        } catch (StravaAthleteNotFoundAsyncException e) {
            logger.warning("Athlete not found while processing activity or activity not found by object ID \n"
                    + "Object ID: " + webhookEvent.objectId() + ", Owner ID: " + webhookEvent.ownerId() + "\n"
                    + "Aspect Type: " + webhookEvent.aspectType() + ", Event Time: " + webhookEvent.eventTime() + "\n"
                    + "Error Message: " + e.getMessage()
            );

            queueSender.sendMessage(
                    queueConfig.getExchangeActivityStrava(),
                    queueConfig.getKeyActivityCreatedStravaDlq(),
                    webhookEvent
            );

        } catch (StravaInternalServerErrorAsyncException e) {
            if (QueueUtil.checkRetryCount(xDeath, queueConfig.getRetryCount())) {
                logger.warning("Max retry count reached for activity: " + webhookEvent.objectId() + ", sending to DLQ");

                queueSender.sendMessage(
                        queueConfig.getExchangeActivityStrava(),
                        queueConfig.getKeyActivityCreatedStravaDlq(),
                        webhookEvent
                );

                return;
            }

            logger.warning("Internal server error while processing activity, retrying...\n"
                    + "Object ID: " + webhookEvent.objectId() + ", Owner ID: " + webhookEvent.ownerId() + "\n"
                    + "Aspect Type: " + webhookEvent.aspectType() + ", Event Time: " + webhookEvent.eventTime()
            );

            throw new StravaInternalServerErrorAsyncException(e.getMessage());
        }
        catch (Exception e) {
            logger.warning("Unexpected error while processing activity, sending to DLQ\n"
                    + "Object ID: " + webhookEvent.objectId() + ", Owner ID: " + webhookEvent.ownerId() + "\n"
                    + "Aspect Type: " + webhookEvent.aspectType() + ", Event Time: " + webhookEvent.eventTime() + "\n"
                    + "Error Message: " + e.getMessage()
            );
            queueSender.sendMessage(
                    queueConfig.getExchangeActivityStrava(),
                    queueConfig.getKeyActivityCreatedStravaDlq(),
                    webhookEvent
            );
        }
    }

    @RabbitListener(queues = "${queue.activity.updated.strava}")
    public void consumeActivityStravaUpdated(WebhookEvent webhookEvent,
                                             @Header(required = false, name = "x-death") Map<String, ?> xDeath) {
        try {
            stravaServices.processWebhookActivityUpdated(webhookEvent);
        } catch (StravaAuthenticationAsyncException e) {
            logger.warning("Authentication error while processing activity, probably the user has not linked their Strava account or token expired\n"
                    + "Object ID: " + webhookEvent.objectId() + ", Owner ID: " + webhookEvent.ownerId() + "\n"
                    + "Aspect Type: " + webhookEvent.aspectType() + ", Event Time: " + webhookEvent.eventTime() + "\n"
                    + "Error Message: " + e.getMessage()
            );

            queueSender.sendMessage(
                    queueConfig.getExchangeActivityStrava(),
                    queueConfig.getKeyActivityUpdatedStravaDlq(),
                    webhookEvent
            );

        } catch (StravaAthleteNotFoundAsyncException e) {
            logger.warning("Athlete not found while processing activity or activity not found by object ID \n"
                    + "Object ID: " + webhookEvent.objectId() + ", Owner ID: " + webhookEvent.ownerId() + "\n"
                    + "Aspect Type: " + webhookEvent.aspectType() + ", Event Time: " + webhookEvent.eventTime() + "\n"
                    + "Error Message: " + e.getMessage()
            );

            queueSender.sendMessage(
                    queueConfig.getExchangeActivityStrava(),
                    queueConfig.getKeyActivityUpdatedStravaDlq(),
                    webhookEvent
            );

        } catch (StravaInternalServerErrorAsyncException e) {
            if (QueueUtil.checkRetryCount(xDeath, queueConfig.getRetryCount())) {
                logger.warning("Max retry count reached for activity: " + webhookEvent.objectId() + ", sending to DLQ");

                queueSender.sendMessage(
                        queueConfig.getExchangeActivityStrava(),
                        queueConfig.getKeyActivityUpdatedStravaDlq(),
                        webhookEvent
                );

                return;
            }

            logger.warning("Internal server error while processing activity, retrying...\n"
                    + "Object ID: " + webhookEvent.objectId() + ", Owner ID: " + webhookEvent.ownerId() + "\n"
                    + "Aspect Type: " + webhookEvent.aspectType() + ", Event Time: " + webhookEvent.eventTime()
            );

            throw new StravaInternalServerErrorAsyncException(e.getMessage());
        }
        catch (Exception e) {
            logger.warning("Unexpected error while processing activity, sending to DLQ\n"
                    + "Object ID: " + webhookEvent.objectId() + ", Owner ID: " + webhookEvent.ownerId() + "\n"
                    + "Aspect Type: " + webhookEvent.aspectType() + ", Event Time: " + webhookEvent.eventTime() + "\n"
                    + "Error Message: " + e.getMessage()
            );
            queueSender.sendMessage(
                    queueConfig.getExchangeActivityStrava(),
                    queueConfig.getKeyActivityUpdatedStravaDlq(),
                    webhookEvent
            );
        }
    }

    @RabbitListener(queues = "${queue.activity.deleted.strava}")
    public void consumeActivityStravaDeleted(WebhookEvent webhookEvent,
                                             @Header(required = false, name = "x-death") Map<String, ?> xDeath) {
        try {
            stravaServices.processWebhookActivityDeleted(webhookEvent);
        } catch (StravaAuthenticationAsyncException e) {
            logger.warning("Authentication error while processing activity, probably the user has not linked their Strava account or token expired\n"
                    + "Object ID: " + webhookEvent.objectId() + ", Owner ID: " + webhookEvent.ownerId() + "\n"
                    + "Aspect Type: " + webhookEvent.aspectType() + ", Event Time: " + webhookEvent.eventTime() + "\n"
                    + "Error Message: " + e.getMessage()
            );

            queueSender.sendMessage(
                    queueConfig.getExchangeActivityStrava(),
                    queueConfig.getKeyActivityDeletedStravaDlq(),
                    webhookEvent
            );

        } catch (StravaAthleteNotFoundAsyncException e) {
            logger.warning("Athlete not found while processing activity or activity not found by object ID \n"
                    + "Object ID: " + webhookEvent.objectId() + ", Owner ID: " + webhookEvent.ownerId() + "\n"
                    + "Aspect Type: " + webhookEvent.aspectType() + ", Event Time: " + webhookEvent.eventTime() + "\n"
                    + "Error Message: " + e.getMessage()
            );

            queueSender.sendMessage(
                    queueConfig.getExchangeActivityStrava(),
                    queueConfig.getKeyActivityDeletedStravaDlq(),
                    webhookEvent
            );

        } catch (StravaInternalServerErrorAsyncException e) {
            if (QueueUtil.checkRetryCount(xDeath, queueConfig.getRetryCount())) {
                logger.warning("Max retry count reached for activity: " + webhookEvent.objectId() + ", sending to DLQ");

                queueSender.sendMessage(
                        queueConfig.getExchangeActivityStrava(),
                        queueConfig.getKeyActivityDeletedStravaDlq(),
                        webhookEvent
                );

                return;
            }

            logger.warning("Internal server error while processing activity, retrying...\n"
                    + "Object ID: " + webhookEvent.objectId() + ", Owner ID: " + webhookEvent.ownerId() + "\n"
                    + "Aspect Type: " + webhookEvent.aspectType() + ", Event Time: " + webhookEvent.eventTime()
            );

            throw new StravaInternalServerErrorAsyncException(e.getMessage());
        }
        catch (Exception e) {
            logger.warning("Unexpected error while processing activity, sending to DLQ\n"
                    + "Object ID: " + webhookEvent.objectId() + ", Owner ID: " + webhookEvent.ownerId() + "\n"
                    + "Aspect Type: " + webhookEvent.aspectType() + ", Event Time: " + webhookEvent.eventTime() + "\n"
                    + "Error Message: " + e.getMessage()
            );
            queueSender.sendMessage(
                    queueConfig.getExchangeActivityStrava(),
                    queueConfig.getKeyActivityDeletedStravaDlq(),
                    webhookEvent
            );
        }
    }
}
