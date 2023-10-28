package com.example.springboot3jwtauthenticationserver.dto;

import com.example.springboot3jwtauthenticationserver.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {

    String token;
    User user;
}
