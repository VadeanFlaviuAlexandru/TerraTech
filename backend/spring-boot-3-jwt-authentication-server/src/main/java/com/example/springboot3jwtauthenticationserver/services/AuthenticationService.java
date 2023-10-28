package com.example.springboot3jwtauthenticationserver.services;

import com.example.springboot3jwtauthenticationserver.dto.JwtAuthenticationResponse;
import com.example.springboot3jwtauthenticationserver.dto.SignInRequest;
import com.example.springboot3jwtauthenticationserver.dto.SignUpRequest;
import com.example.springboot3jwtauthenticationserver.models.Role;
import com.example.springboot3jwtauthenticationserver.models.User;
import com.example.springboot3jwtauthenticationserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    // The UserRepository for database operations related to users
    private final UserRepository userRepository;

    // The UserService for user-related business logic and operations
    private final UserService userService;

    // A password encoder for securing user passwords
    private final PasswordEncoder passwordEncoder;

    // A service for JWT (JSON Web Token) operations
    private final JwtService jwtService;

    // An authentication manager for user authentication
    private final AuthenticationManager authenticationManager;

    /**
     * Signup a new user with the provided SignUpRequest.
     *
     * @param request The SignUpRequest containing user information.
     * @return A JwtAuthenticationResponse with a JWT token.
     */
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        // Create a new User instance with the provided information
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.MANAGER)
                .build();

        // Save the user to the database
        user = userService.save(user);

        // Generate a JWT token for the user and return it in a JwtAuthenticationResponse
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    /**
     * Sign in a user with the provided SignInRequest.
     *
     * @param request The SignInRequest containing email and password.
     * @return A JwtAuthenticationResponse with a JWT token upon successful sign-in.
     * @throws IllegalArgumentException if the email or password is invalid.
     */
    public JwtAuthenticationResponse signin(SignInRequest request) {
        // Authenticate the user using the provided email and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        // Find the user by email, or throw an exception if not found
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        // Generate a JWT token for the user and return it in a JwtAuthenticationResponse
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).user(user).build();
    }
}
