package com.example.springboot3jwtauthenticationserver.config;

import com.example.springboot3jwtauthenticationserver.models.Activity;
import com.example.springboot3jwtauthenticationserver.models.Manager;
import com.example.springboot3jwtauthenticationserver.models.Role;
import com.example.springboot3jwtauthenticationserver.models.User;
import com.example.springboot3jwtauthenticationserver.repositories.ActivityRepository;
import com.example.springboot3jwtauthenticationserver.repositories.ManagerRepository;
import com.example.springboot3jwtauthenticationserver.repositories.ProductRepository;
import com.example.springboot3jwtauthenticationserver.repositories.UserRepository;
import com.example.springboot3jwtauthenticationserver.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedDataConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final ActivityRepository activityRepository;
    private final ManagerRepository managerRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {

//        if (userRepository.count() == 0 && activityRepository.count() == 0 && managerRepository.count() == 0 && productRepository.count() == 0) {
//
//            List<User> managerUsers = new ArrayList<>();
//            List<Activity> userActivities = new ArrayList<>();
//            User adminUser = null;
//
//            User admin = User.builder()
//                    .firstName("Admin")
//                    .lastName("User")
//                    .phone("1122334456")
//                    .email("admin@admin.com")
//                    .password(passwordEncoder.encode("admin"))
//                    .role(Role.ADMIN)
//                    .build();
//
//            Manager adminManager = Manager.builder()
//                    .firstName("Admin")
//                    .lastName("Manager")
//                    .phone("1313422423")
//                    .email("manager@admin.com")
//                    .password(passwordEncoder.encode("admin"))
//                    .role(Role.MANAGER)
//                    .employees(managerUsers)
//                    .build();
//
//            Activity userActivity = Activity.builder()
//                    .description("Main admin activity")
//                    .createdAt(LocalDate.now())
//                    .peopleNotifiedAboutProduct(1)
//                    .peopleSoldTo(1)
//                    .user(adminUser)
//                    .build();
//
//
//            adminUser = User.builder()
//                    .firstName("Dummy")
//                    .lastName("User")
//                    .phone("1111111111")
//                    .email("dummy@admin.com")
//                    .password(passwordEncoder.encode("admin"))
//                    .role(Role.USER)
//                    .manager(adminManager)
//                    .activities(userActivities)
//                    .build();
//
//
//            managerUsers.add(adminManager);
//            userActivities.add(userActivity);
//
//            userRepository.save(adminUser);
//            userService.save(admin);
//            managerRepository.save(adminManager);
//            activityRepository.save(userActivity);
//        }
    }
}
