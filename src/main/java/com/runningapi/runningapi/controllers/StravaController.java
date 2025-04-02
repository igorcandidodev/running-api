package com.runningapi.runningapi.controllers;

import com.runningapi.runningapi.dto.strava.request.WebhookEvent;
import com.runningapi.runningapi.dto.strava.response.CallbackResponse;
import com.runningapi.runningapi.queue.producer.QueueSender;
import com.runningapi.runningapi.service.strava.StravaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/strava/")
public class StravaController {

    @Autowired
    private StravaServices stravaServices;

    @Autowired
    private QueueSender queueSender;

    @Value("${exchange.activity.update.strava}")
    private String exchangeActivityStrava;

    @Value("${key.activity.update.strava}")
    private String keyActivityStrava;

    @GetMapping("/athlete-activity/webhook")
    public ResponseEntity<CallbackResponse> validationWebhook(@RequestParam("hub.mode") String hubMode,
                                                              @RequestParam("hub.challenge") String hubChallenge,
                                                              @RequestParam("hub.verify_token") String hubVerifyToken) {
        if (hubMode.equals("subscribe") && hubVerifyToken.equals(stravaServices.getVerifyTokenCallback())) {
            return ResponseEntity.ok().body(new CallbackResponse(hubChallenge));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).build();
        }
    }

    @PostMapping("/athlete-activity/webhook")
    public ResponseEntity<Void> eventActivity(@RequestBody WebhookEvent body) {

        if (body != null) {
            switch (body.aspectType()) {
                case "create" -> {
                    System.out.println("Received create event: " + body);
                    queueSender.sendMessage(exchangeActivityStrava, keyActivityStrava, body);
                }
                case "update" -> {
                    System.out.println("Received update event: " + body);
                }
                case "delete" -> {
                    System.out.println("Received delete event: " + body);
                }
                default -> {
                    System.out.println("Unknown event type: " + body.aspectType());
                }
            }
        }

        return ResponseEntity.ok().build();
    }

}
