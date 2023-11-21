package TerraTech.BranchManagementBackend.controllers.authControllers;

import TerraTech.BranchManagementBackend.dto.auth.SignInResponse;
import TerraTech.BranchManagementBackend.dto.auth.SignInRequest;
import TerraTech.BranchManagementBackend.dto.auth.SignUpRequest;
import TerraTech.BranchManagementBackend.services.authServices.AuthenticationService;
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

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {
        return authenticationService.signup(request);
    }

    @PostMapping("/signIn")
    public SignInResponse signIn(@RequestBody SignInRequest request) {
        return authenticationService.signin(request);
    }
}