package com.example.springboot3jwtauthenticationserver.services;

import com.example.springboot3jwtauthenticationserver.models.User;
import com.example.springboot3jwtauthenticationserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
// used to indicate that a class is a service component in a Spring-based application. Service components are typically used to encapsulate business logic, perform operations, and manage data for a specific part of the application.
@RequiredArgsConstructor //generates a constructor that initializes all fields of the class
public class UserService {

    private final UserRepository userRepository;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                // Load user details from the UserRepository by email (username)
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    public User save(User newUser) {
        if (newUser.getId() == null) {
            newUser.setCreatedAt(LocalDate.now());
        }
        return userRepository.save(newUser);
    }


}
