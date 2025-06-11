package com.runningapi.runningapi.service.strava;

import com.runningapi.runningapi.configuration.StravaConfig;
import com.runningapi.runningapi.dto.strava.request.WebhookEvent;
import com.runningapi.runningapi.dto.strava.response.UserAuthenticationResponse;
import com.runningapi.runningapi.dto.strava.response.activity.StravaActivityResponse;
import com.runningapi.runningapi.exceptions.StravaInternalServerErrorAsyncException;
import com.runningapi.runningapi.model.enums.StatusActivity;
import com.runningapi.runningapi.exceptions.StravaAthleteNotFoundAsyncException;
import com.runningapi.runningapi.exceptions.StravaAuthenticationAsyncException;
import com.runningapi.runningapi.mapper.StravaAuthenticationMapper;
import com.runningapi.runningapi.model.strava.StravaAuthentication;
import com.runningapi.runningapi.repository.AthleteRepository;
import com.runningapi.runningapi.repository.StravaAuthenticationRepository;
import com.runningapi.runningapi.repository.UserRepository;
import com.runningapi.runningapi.security.UserDetailsSecurity;
import com.runningapi.runningapi.service.TrainingPerformedService;
import com.runningapi.runningapi.service.TrainingService;
import com.runningapi.runningapi.utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.ZonedDateTime;

@Service
public class StravaServices {

    @Autowired
    private StravaConfig stravaConfig;

    @Autowired
    private StravaAuthenticationRepository authenticationRepositoy;

    @Autowired
    private StravaAuthenticationMapper stravaAuthenticationMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private TrainingPerformedService trainingPerformedService;

    private final WebClient webClient;

    public StravaServices(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String getRedirectUriAuthorization() {
        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return  stravaConfig.getAuthUri() +
                "authorize?" +
                "client_id=" +
                stravaConfig.getClientId() +
                "&response_type=code" +
                "&redirect_uri=" +
                stravaConfig.getRedirectUri() +
                "&approval_prompt=force" +
                "&scope=" +
                stravaConfig.getScope() +
                "&state=" + userDetails.getUsername();
    }

    public void processAuthorizationCode(String code, String state) {
        var url = new StringBuilder();
        url.append(stravaConfig.getAuthUri())
                .append("token")
                .append("?client_id=")
                .append(stravaConfig.getClientId())
                .append("&client_secret=")
                .append(stravaConfig.getClientSecret())
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");

        var response = webClient.post()
                .uri(url.toString())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class).map(body -> new RuntimeException("Erro ao se comunicar com a  API do Strava: " + body)))
                .bodyToMono(UserAuthenticationResponse.class)
                .block();

        var stravaAuthentication = authenticationRepositoy.findByUserEmail(state);

        if(stravaAuthentication == null) {
            var user = userRepository.findByEmail(state).orElseThrow(() -> new RuntimeException("User not found"));
            stravaAuthentication = stravaAuthenticationMapper.toEntity(response, user);
        } else {
            if(response != null) {
                updateStravaAuthentication(response, stravaAuthentication);
            }
        }

        athleteRepository.save(stravaAuthentication.getAthlete());
        authenticationRepositoy.save(stravaAuthentication);
    }

    public StravaAuthentication refreshAccessToken(StravaAuthentication stravaAuthentication) {
        var url = new StringBuilder();
        url.append(stravaConfig.getAuthUri())
                .append("token")
                .append("?client_id=")
                .append(stravaConfig.getClientId())
                .append("&client_secret=")
                .append(stravaConfig.getClientSecret())
                .append("&grant_type=refresh_token")
                .append("&refresh_token=")
                .append(stravaAuthentication.getRefreshToken());

        var response = webClient.post()
                .uri(url.toString())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class).map(body -> new RuntimeException("Erro ao se comunicar com a  API do Strava: " + body)))
                .bodyToMono(UserAuthenticationResponse.class)
                .block();

        if (response == null) {
           throw new RuntimeException("Erro ao obter o token de acesso do Strava");
        }
        updateStravaAuthentication(response, stravaAuthentication);
        return authenticationRepositoy.save(stravaAuthentication);
    }

    private void updateStravaAuthentication(UserAuthenticationResponse newAuthentication, StravaAuthentication oldAuthentication) {
        oldAuthentication.setAccessToken(newAuthentication.accessToken());
        oldAuthentication.setExpiresAt(newAuthentication.expiresAt());
        oldAuthentication.setExpiresIn(newAuthentication.expiresIn());
        oldAuthentication.setRefreshToken(newAuthentication.refreshToken());
        oldAuthentication.setTokenType(newAuthentication.tokenType());
    }

    public String getVerifyTokenCallback() {
        return stravaConfig.getVerifyTokenCallback();
    }

    public void processWebhookActivityCreated(WebhookEvent event) {

        var athlete = athleteRepository.findById(event.ownerId()).orElseThrow(() -> new StravaAthleteNotFoundAsyncException("Athlete Strava not found by owner Id: " + event.ownerId() + ", received in webhook event by Strava"));

        var activityAtStrava = getActivityAtStrava(event, athlete.getAuthentication());

        if(activityAtStrava == null) {
            throw new StravaAthleteNotFoundAsyncException("Activity Strava not found by activity Id: " + event.objectId() + ", received in webhook event by Strava");
        }

        if(!activityAtStrava.type().equals("Run")) {
            throw new StravaAthleteNotFoundAsyncException("Activity Strava not is a Run by activity Id: " + event.objectId() + ", received in webhook event by Strava");
        }

        trainingService.linkTrainingWithTrainingPerformed(activityAtStrava,
                athlete.getAuthentication().getUser().getId());
    }

    public void processWebhookActivityUpdated(WebhookEvent event) {

        if(event.updates().title() != null) {
            var training = trainingPerformedService.findTrainingPerformedByStravaId(event.objectId());

            if(training != null) {
                training.setTitle(event.updates().title());
                trainingPerformedService.saveTraining(training);
            }
        }

    }

    public void processWebhookActivityDeleted(WebhookEvent event) {
        var training = trainingService.findTrainingByStravaId(event.objectId());
        if(training != null) {
            training.setStatusActivity(StatusActivity.PENDING);
            training.setTrainingPerformed(null);

            trainingService.saveTraining(training);
        }
    }

    public StravaActivityResponse getActivityAtStrava(WebhookEvent event, StravaAuthentication stravaAuthentication) {

        if(stravaAuthentication == null) {
            throw new StravaAuthenticationAsyncException("User Authentication Strava not found don't being possible to get activity");
        }

        var tokenExpires = DateConverter.convertToZonedDateTime(stravaAuthentication.getExpiresAt());
        if (tokenExpires.isBefore(ZonedDateTime.now())) {
            stravaAuthentication = refreshAccessToken(stravaAuthentication);
            tokenExpires = DateConverter.convertToZonedDateTime(stravaAuthentication.getExpiresAt());
        }

        if (tokenExpires.isBefore(ZonedDateTime.now())) {
            throw new StravaAuthenticationAsyncException("Strava access token expired, please re-authenticate.");
        }

        var url = new StringBuilder();
        url.append(stravaConfig.getApiUri().endsWith("/") ? stravaConfig.getApiUri() : stravaConfig.getApiUri() + "/")
                .append("activities/")
                .append(event.objectId());

        return webClient.get()
                .uri(url.toString())
                .header("Authorization", "Bearer " + stravaAuthentication.getAccessToken())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(body -> new StravaAuthenticationAsyncException("Exception Status Code 4XX while trying communicate with Strava API to get Activity" + body)))
                .onStatus(HttpStatusCode::is5xxServerError,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(body -> new StravaInternalServerErrorAsyncException("Exception Status Code 5XX while trying communicate with Strava API to get Activity" + body)))
                .bodyToMono(StravaActivityResponse.class)
                .block();
    }
}
