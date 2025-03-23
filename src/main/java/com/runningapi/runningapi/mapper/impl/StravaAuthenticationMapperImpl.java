package com.runningapi.runningapi.mapper.impl;

import com.runningapi.runningapi.dto.strava.response.AthleteResponse;
import com.runningapi.runningapi.dto.strava.response.UserAuthenticationResponse;
import com.runningapi.runningapi.mapper.StravaAuthenticationMapper;
import com.runningapi.runningapi.model.User;
import com.runningapi.runningapi.model.strava.Athlete;
import com.runningapi.runningapi.model.strava.StravaAuthentication;
import org.springframework.stereotype.Component;

@Component
public class StravaAuthenticationMapperImpl implements StravaAuthenticationMapper {

    @Override
    public StravaAuthentication toEntity(UserAuthenticationResponse userAuthenticationResponse, User user) {
        return new StravaAuthentication(userAuthenticationResponse.tokenType(), userAuthenticationResponse.expiresAt(),
                                        userAuthenticationResponse.expiresIn(), userAuthenticationResponse.refreshToken(),
                                        userAuthenticationResponse.accessToken(), user,
                                        toAthleteEntity(userAuthenticationResponse.athleteResponse()));
    }

    private Athlete toAthleteEntity(AthleteResponse athleteResponse) {
        Athlete athlete = new Athlete();
        athlete.setId(athleteResponse.id());
        athlete.setUsername(athleteResponse.username());
        athlete.setResourceState(athleteResponse.resourceState());
        athlete.setFirstname(athleteResponse.firstname());
        athlete.setLastname(athleteResponse.lastname());
        athlete.setBio(athleteResponse.bio());
        athlete.setCity(athleteResponse.city());
        athlete.setState(athleteResponse.state());
        athlete.setCountry(athleteResponse.country());
        athlete.setSex(athleteResponse.sex());
        athlete.setPremium(athleteResponse.premium());
        athlete.setSummit(athleteResponse.summit());
        athlete.setCreatedAt(athleteResponse.created_at());
        athlete.setUpdatedAt(athleteResponse.updated_at());
        athlete.setBadgeTypeId(athleteResponse.badge_type_id());
        athlete.setWeight(athleteResponse.weight());
        athlete.setProfileMedium(athleteResponse.profile_medium());
        athlete.setProfile(athleteResponse.profile());
        athlete.setFriend(athleteResponse.friend());
        athlete.setFollower(athleteResponse.follower());
        return athlete;
    }
}
