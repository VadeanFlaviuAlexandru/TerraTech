package com.example.springboot3jwtauthenticationserver.controllers;

import com.example.springboot3jwtauthenticationserver.dto.JwtAuthenticationResponse;
import com.example.springboot3jwtauthenticationserver.dto.SignInRequest;
import com.example.springboot3jwtauthenticationserver.dto.SignUpRequest;
import com.example.springboot3jwtauthenticationserver.repositories.UserRepository;
import com.example.springboot3jwtauthenticationserver.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/TerraTechInc")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public JwtAuthenticationResponse signUp(@RequestBody SignUpRequest request) {
        return authenticationService.signup(request);
    }

    @PostMapping("/signin")
    public JwtAuthenticationResponse signIn(@RequestBody SignInRequest request) {
        return authenticationService.signin(request);
    }
}
