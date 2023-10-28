package com.example.springboot3jwtauthenticationserver.controllers;

import com.example.springboot3jwtauthenticationserver.dto.SignUpRequest;
import com.example.springboot3jwtauthenticationserver.dto.UserProfileUpdateRequest;
import com.example.springboot3jwtauthenticationserver.exception.UserNotFoundException;
import com.example.springboot3jwtauthenticationserver.models.Manager;
import com.example.springboot3jwtauthenticationserver.models.Role;
import com.example.springboot3jwtauthenticationserver.models.User;
import com.example.springboot3jwtauthenticationserver.repositories.ManagerRepository;
import com.example.springboot3jwtauthenticationserver.repositories.UserRepository;
import com.example.springboot3jwtauthenticationserver.services.JwtService;
import com.example.springboot3jwtauthenticationserver.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/TerraTechInc")
@RequiredArgsConstructor
public class ManagerController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    private final ManagerRepository managerRepository;

    @PostMapping("/managers/addUser")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> addEmployee(@RequestHeader("Authorization") String token, @RequestBody SignUpRequest request) {
        try {
            Manager manager = jwtService.extractManager(token);
            if (manager == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or unauthorized manager.");
            }

            User user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .phone(request.getPhone())
                    .createdAt(LocalDate.now())
                    .role(Role.USER)
                    .manager(manager)
                    .build();
            userService.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            // Handle any other exceptions that might occur
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the user.");
        }
    }

    @PutMapping("/managers/editUser/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public User updateUserProfile(@PathVariable Long id, @RequestBody UserProfileUpdateRequest newUser) {
        return userRepository.findById(id).map(user -> {
            newUser.getEmail().ifPresent(email -> user.setEmail(email));
            newUser.getRole().ifPresent(role -> user.setRole(role));
            newUser.getFirstName().ifPresent(firstName -> user.setFirstName(firstName));
            newUser.getLastName().ifPresent(lastName -> user.setLastName(lastName));
            newUser.getPhone().ifPresent(phone -> user.setPhone(phone));
            return userRepository.save(user);
        }).orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping("/managers/user/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public User getUserProfile(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping("/managers/users")
    @PreAuthorize("hasRole('MANAGER')")
    public List<User> getUsers(String token) {
        return userRepository.findByManagerId(jwtService.extractId(token));
    }

    @DeleteMapping("/managers/users/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> deleteUserProfile(@PathVariable Long id) {
        try {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted successfully");
        } catch (EmptyResultDataAccessException ex) {
            // If the product with the given ID does not exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        } catch (Exception ex) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }


}
