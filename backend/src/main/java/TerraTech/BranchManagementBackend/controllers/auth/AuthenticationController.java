package TerraTech.BranchManagementBackend.controllers.auth;

import TerraTech.BranchManagementBackend.dto.auth.SignInResponse;
import TerraTech.BranchManagementBackend.dto.auth.SignInRequest;
import TerraTech.BranchManagementBackend.dto.auth.SignUpRequest;
import TerraTech.BranchManagementBackend.exceptions.auth.AuthenticateException;
import TerraTech.BranchManagementBackend.exceptions.manager.RegisterException;
import TerraTech.BranchManagementBackend.repositories.UserRepository;
import TerraTech.BranchManagementBackend.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/TerraTechInc")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new RegisterException("This email already exists!");
        });
        authenticationService.signUp(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/signIn")
    public SignInResponse signIn(@RequestBody SignInRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthenticateException("No account with this email found!"));
        return authenticationService.signIn(user, request);
    }
}