package com.example.springboot3jwtauthenticationserver.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/TerraTechInc")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/anon")
    public String anonEndPoint() {
        return "everyone can see this";
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('USER')|| hasRole('MANAGER') || hasRole('ADMIN')")
    public String usersEndPoint() {
        return "ONLY users can see this";
    }

    @GetMapping("/admins")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminsEndPoint() {
        return "ONLY admins can see this";
    }

    @GetMapping("/managers")
    @PreAuthorize("hasRole('MANAGER') || hasRole('ADMIN')")
    public String managersEndPoint() {
        return "ONLY managers or above can see this";
    }
}