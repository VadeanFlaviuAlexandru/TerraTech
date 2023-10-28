package com.example.springboot3jwtauthenticationserver.dto;

import com.example.springboot3jwtauthenticationserver.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
public class UserProfileUpdateRequest {
    private Optional<String> firstName;
    private Optional<String> lastName;
    private Optional<String> email;
    private Optional<String> phone;
    private Optional<String> password;
    private Optional<Role> role;

    public UserProfileUpdateRequest() {
        this.firstName = Optional.empty();
        this.lastName = Optional.empty();
        this.email = Optional.empty();
        this.phone = Optional.empty();
        this.password = Optional.empty();
        this.role = Optional.empty();
    }
}