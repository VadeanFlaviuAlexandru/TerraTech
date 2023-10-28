package com.example.springboot3jwtauthenticationserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The SignInRequest class represents the data transfer object (DTO) used for user sign-in.
 * It contains user credentials required for authentication, including their email and password.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

    String email;
    String password;
}
