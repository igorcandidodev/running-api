package com.runningapi.runningapi.mapper.impl;

import com.runningapi.runningapi.dto.strava.response.UserAuthenticationResponse;
import com.runningapi.runningapi.mapper.StravaAuthenticationMapper;
import com.runningapi.runningapi.model.strava.StravaAuthentication;
import org.springframework.stereotype.Component;

@Component
public class StravaAuthenticationMapperImpl implements StravaAuthenticationMapper {

    @Override
    public StravaAuthentication toEntity(UserAuthenticationResponse userAuthenticationResponse) {
        var athlete = userAuthenticationResponse.athleteResponse();
        var authentication = new StravaAuthentication();
        return authentication;
    }
}
