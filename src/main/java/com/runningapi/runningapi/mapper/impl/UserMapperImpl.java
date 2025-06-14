package com.runningapi.runningapi.mapper.impl;

import com.runningapi.runningapi.dto.response.user.PhysicalActivityResponseDto;
import com.runningapi.runningapi.dto.response.user.PhysicalLimitationResponseDto;
import com.runningapi.runningapi.dto.response.user.UserResponseDto;
import com.runningapi.runningapi.mapper.UserMapper;
import com.runningapi.runningapi.model.PhysicalActivity;
import com.runningapi.runningapi.model.PhysicalLimitation;
import com.runningapi.runningapi.model.User;
import com.runningapi.runningapi.model.enums.Provider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDto toResponseDto(Long id, String name, String email, Provider provider) {
        return new UserResponseDto(id, name, email, provider, null, null, null);
    }

    @Override
    public UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(user.getId(), user.getFullName(), user.getEmail(), user.getProvider(), user.getBirthDate(),
                                user.getPhysicalLimitation() != null ? mapPhysicalLimitations(user.getPhysicalLimitation()) : null,
                                user.getPhysicalActivityHistory() != null ? mapPhysicalActivities(user.getPhysicalActivityHistory()) : null
                );
    }

    private List<PhysicalLimitationResponseDto> mapPhysicalLimitations(List<PhysicalLimitation> physicalLimitations) {
        return physicalLimitations.stream().map(limitation -> new PhysicalLimitationResponseDto(limitation.isFeltPain(), limitation.getDescription())).toList();
    }

    private List<PhysicalActivityResponseDto> mapPhysicalActivities(List<PhysicalActivity> physicalActivities) {
        return physicalActivities.stream().map(activity -> new PhysicalActivityResponseDto(activity.getSportActivity(), activity.getFrequency())).toList();
    }
}
