package com.example.springboot3jwtauthenticationserver.services;

import com.example.springboot3jwtauthenticationserver.models.Activity;
import com.example.springboot3jwtauthenticationserver.repositories.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    public Activity save(Activity newActivity) {
        if (newActivity.getId() == null) {
            newActivity.setCreatedAt(LocalDate.now());
        }
        return activityRepository.save(newActivity);
    }
}
