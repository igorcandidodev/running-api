package com.runningapi.runningapi.service.strava;

import com.runningapi.runningapi.configuration.StravaConfig;
import com.runningapi.runningapi.dto.strava.response.UserAuthenticationResponse;
import com.runningapi.runningapi.mapper.StravaAuthenticationMapper;
import com.runningapi.runningapi.model.strava.StravaAuthentication;
import com.runningapi.runningapi.repository.AthleteRepository;
import com.runningapi.runningapi.repository.StravaAuthenticationRepository;
import com.runningapi.runningapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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

    private final WebClient webClient;

    public StravaServices(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String getRedirectUriAuthorization(Long id) {
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
                "&state=" + id;
    }

    public void processAuthorizationCode(String code, Long state) {
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

        var stravaAuthentication = authenticationRepositoy.findByUserId(state);

        if(stravaAuthentication == null) {
            var user = userRepository.findById(state).orElseThrow(() -> new RuntimeException("User not found"));
            stravaAuthentication = stravaAuthenticationMapper.toEntity(response, user);
        } else {
            if(response != null) {
                updateStravaAuthentication(response, stravaAuthentication);
            }
        }

        athleteRepository.save(stravaAuthentication.getAthlete());
        authenticationRepositoy.save(stravaAuthentication);
    }

    public void refreshAccessToken(StravaAuthentication stravaAuthentication) {
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

        if(response != null) {
            updateStravaAuthentication(response, stravaAuthentication);
            authenticationRepositoy.save(stravaAuthentication);
        }

    }

    private void updateStravaAuthentication(UserAuthenticationResponse newAuthentication, StravaAuthentication oldAuthentication) {
        oldAuthentication.setAccessToken(newAuthentication.accessToken());
        oldAuthentication.setExpiresAt(newAuthentication.expiresAt());
        oldAuthentication.setExpiresIn(newAuthentication.expiresIn());
        oldAuthentication.setRefreshToken(newAuthentication.refreshToken());
        oldAuthentication.setTokenType(newAuthentication.tokenType());
    }
}
