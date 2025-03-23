package com.runningapi.runningapi.mapper.impl;

import com.runningapi.runningapi.dto.strava.response.UserAuthenticationResponse;
import com.runningapi.runningapi.mapper.StravaAuthenticationMapper;
import com.runningapi.runningapi.model.User;
import com.runningapi.runningapi.model.strava.StravaAuthentication;
import org.springframework.stereotype.Component;

@Component
public class StravaAuthenticationMapperImpl implements StravaAuthenticationMapper {

    @Override
    public StravaAuthentication toEntity(UserAuthenticationResponse userAuthenticationResponse, User user) {
        return new StravaAuthentication(userAuthenticationResponse.tokenType(), userAuthenticationResponse.expiresAt(), userAuthenticationResponse.expiresIn(), userAuthenticationResponse.refreshToken(), userAuthenticationResponse.accessToken(), user);
    }
}
