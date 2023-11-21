package TerraTech.BranchManagementBackend.controllers.authControllers;

import TerraTech.BranchManagementBackend.dto.auth.signInResponse;
import TerraTech.BranchManagementBackend.dto.jwt.jwtAuthenticationResponse;
import TerraTech.BranchManagementBackend.dto.auth.signInRequest;
import TerraTech.BranchManagementBackend.dto.auth.signUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/TerraTechInc")
@RequiredArgsConstructor
public class authenticationController {

    private final TerraTech.BranchManagementBackend.services.authServices.authenticationService authenticationService;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody signUpRequest request) {
        return authenticationService.signup(request);
    }

    @PostMapping("/signIn")
    public signInResponse signIn(@RequestBody signInRequest request) {
        return authenticationService.signin(request);
    }
}