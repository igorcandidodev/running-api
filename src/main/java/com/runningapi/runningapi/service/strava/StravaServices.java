package com.runningapi.runningapi.service.strava;

import com.runningapi.runningapi.configuration.StravaConfig;
import com.runningapi.runningapi.dto.strava.response.UserAuthenticationResponse;
import com.runningapi.runningapi.repository.StravaAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class StravaServices {

    @Autowired
    private StravaConfig stravaConfig;

    @Autowired
    private StravaAuthenticationRepository authenticationRepositoy;

    private final WebClient webClient;

    public StravaServices(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String getRedirectUriAuthorization() {
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
                "&state=runningapi";
    }

    public UserAuthenticationResponse getAuthorizationCode(String code) {
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
                .bodyToMono(UserAuthenticationResponse.class)
                .block();

        return response;
    }
}
