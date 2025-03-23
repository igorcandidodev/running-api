package com.runningapi.runningapi.mapper;

import com.runningapi.runningapi.dto.strava.response.UserAuthenticationResponse;
import com.runningapi.runningapi.model.User;
import com.runningapi.runningapi.model.strava.StravaAuthentication;

public interface StravaAuthenticationMapper {

    StravaAuthentication toEntity(UserAuthenticationResponse userAuthenticationResponse, User user);

}
