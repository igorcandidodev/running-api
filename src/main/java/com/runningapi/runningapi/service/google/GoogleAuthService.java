package com.runningapi.runningapi.service.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.runningapi.runningapi.model.google.GoogleAuthentication;
import com.runningapi.runningapi.model.User;
import com.runningapi.runningapi.repository.GoogleAuthenticationRepository;
import com.runningapi.runningapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class GoogleAuthService {

    private final GoogleAuthenticationRepository googleAuthRepository;
    private final UserRepository userRepository;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    public GoogleAuthService(GoogleAuthenticationRepository googleAuthRepository, UserRepository userRepository) {
        this.googleAuthRepository = googleAuthRepository;
        this.userRepository = userRepository;
    }

    public String getRedirectUriAuthorization() {
        return "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=" + clientId +
                "&response_type=code" +
                "&redirect_uri=" + redirectUri +
                "&scope=openid%20profile%20email" +
                "&access_type=offline" +
                "&prompt=consent";
    }

    public User processAuthorizationCode(String code) throws IOException {
        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                "https://oauth2.googleapis.com/token",
                clientId,
                clientSecret,
                code,
                redirectUri)
                .execute();

        GoogleIdToken idToken = tokenResponse.parseIdToken();
        GoogleIdToken.Payload payload = idToken.getPayload();
        String userEmail = payload.getEmail();
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        User user;

        if (optionalUser.isPresent()) {
            handleExistingUser(optionalUser.get(), tokenResponse);
            optionalUser.get().setProvider("GOOGLE");

            userRepository.save(optionalUser.get());

            user = optionalUser.get();
        } else {
            User newUser = createUser(payload);
            newUser.setProvider("GOOGLE");
            handleExistingUser(newUser, tokenResponse);
            user = newUser;
        }

        return user;
    }

    private void handleExistingUser(User user, GoogleTokenResponse tokenResponse) {
        GoogleAuthentication googleAuth = googleAuthRepository.findByUserId(user.getId());
        if (googleAuth == null) {
            googleAuth = new GoogleAuthentication();
            googleAuth.setUser(user);
        }
        googleAuth.setAccessToken(tokenResponse.getAccessToken());
        googleAuth.setRefreshToken(tokenResponse.getRefreshToken());
        googleAuth.setExpiresIn(tokenResponse.getExpiresInSeconds());
        googleAuth.setTokenType(tokenResponse.getTokenType());
        googleAuthRepository.save(googleAuth);
    }

    private User createUser(GoogleIdToken.Payload payload) {
        User user = new User((String) payload.get("name"), payload.getEmail());
        return userRepository.save(user);
    }

    public void refreshAccessToken(GoogleAuthentication googleAuth) throws IOException {
        GoogleTokenResponse tokenResponse = new GoogleRefreshTokenRequest(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                googleAuth.getRefreshToken(),
                clientId,
                clientSecret)
                .execute();

        googleAuth.setAccessToken(tokenResponse.getAccessToken());
        googleAuthRepository.save(googleAuth);
    }
}