package com.runningapi.runningapi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueActivityStravaConfig {

    @Value("${exchange.activity.update.strava}")
    private String exchangeActivityStrava;

    @Value("${exchange.activity.update.strava.retry}")
    private String exchangeActivityStravaRetry;

    @Value("${queue.activity.created.strava}")
    private String queueActivityCreatedStrava;

    @Value("${queue.activity.updated.strava}")
    private String queueActivityUpdatedStrava;

    @Value("${queue.activity.deleted.strava}")
    private String queueActivityDeletedStrava;

    @Value("${key.activity.created.strava}")
    private String keyActivityCreatedStrava;

    @Value("${key.activity.updated.strava}")
    private String keyActivityUpdatedStrava;

    @Value("${key.activity.deleted.strava}")
    private String keyActivityDeletedStrava;

    @Value("${key.activity.updated.strava.dlq}")
    private String keyActivityUpdatedStravaDlq;

    @Value("${key.activity.created.strava.dlq}")
    private String keyActivityCreatedStravaDlq;

    @Value("${key.activity.deleted.strava.dlq}")
    private String keyActivityDeletedStravaDlq;

    @Value("${rabbitmq.webhook.activity.strava.retry}")
    private Long retryCount;

    @Value("${rabbitmq.retry.delay-in-ms}")
    private Integer retryDelayInMs;

    public String getExchangeActivityStrava() {
        return exchangeActivityStrava;
    }

    public String getQueueActivityCreatedStrava() {
        return queueActivityCreatedStrava;
    }

    public String getQueueActivityUpdatedStrava() {
        return queueActivityUpdatedStrava;
    }

    public String getQueueActivityDeletedStrava() {
        return queueActivityDeletedStrava;
    }

    public String getKeyActivityCreatedStrava() {
        return keyActivityCreatedStrava;
    }

    public String getKeyActivityUpdatedStrava() {
        return keyActivityUpdatedStrava;
    }

    public String getKeyActivityDeletedStrava() {
        return keyActivityDeletedStrava;
    }

    public String getExchangeActivityStravaRetry() {
        return exchangeActivityStravaRetry;
    }

    public String getKeyActivityUpdatedStravaDlq() {
        return keyActivityUpdatedStravaDlq;
    }

    public String getKeyActivityCreatedStravaDlq() {
        return keyActivityCreatedStravaDlq;
    }

    public String getKeyActivityDeletedStravaDlq() {
        return keyActivityDeletedStravaDlq;
    }

    public Long getRetryCount() {
        return retryCount;
    }

    public Integer getRetryDelayInMs() {
        return retryDelayInMs;
    }
}
