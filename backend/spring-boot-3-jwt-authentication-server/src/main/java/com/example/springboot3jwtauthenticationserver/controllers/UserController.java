package com.example.springboot3jwtauthenticationserver.controllers;

import com.example.springboot3jwtauthenticationserver.dto.ActivityRequest;
import com.example.springboot3jwtauthenticationserver.models.Activity;
import com.example.springboot3jwtauthenticationserver.services.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/TerraTechInc")
@RequiredArgsConstructor
public class UserController {

    private final ActivityService activityService;

    @PostMapping("/addActivity")
    public Activity addActivity(String token, @RequestBody ActivityRequest request) {
        var activity = Activity.builder()
                .description(request.getDescription())
                .peopleNotifiedAboutProduct(request.getPeopleNotifiedAboutProduct())
                .peopleSoldTo(request.getPeopleSoldTo())
                .build();

        activityService.save(activity);
        return activity;
    }


}