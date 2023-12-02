package TerraTech.BranchManagementBackend.services.auth;

import TerraTech.BranchManagementBackend.dto.auth.SignInRequest;
import TerraTech.BranchManagementBackend.dto.auth.SignInResponse;
import TerraTech.BranchManagementBackend.dto.auth.SignUpRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.ChartRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.ReportRequest;
import TerraTech.BranchManagementBackend.enums.Role;
import TerraTech.BranchManagementBackend.exceptions.auth.AuthenticateException;
import TerraTech.BranchManagementBackend.models.User;
import TerraTech.BranchManagementBackend.repositories.ReportRepository;
import TerraTech.BranchManagementBackend.repositories.UserRepository;
import TerraTech.BranchManagementBackend.services.jwt.JwtService;
import TerraTech.BranchManagementBackend.services.user.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final ReportRepository reportRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ManagerService managerService;

    public User signUp(SignUpRequest request) {
        User user = User.builder().firstName(request.getFirstName())
                .lastName(request.getLastName()).email(request.getEmail()).phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword())).role(Role.ROLE_MANAGER)
                .createdAt(LocalDate.now()).status(true)
                .build();
        return userRepository.save(user);
    }

    public SignInResponse signIn(User user, SignInRequest request) {
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthenticateException("Wrong password!");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var jwt = jwtService.generateToken(user);
        ChartRequest data = managerService.chartInfo(user.getId());
        List<ReportRequest> reports = reportRepository.findReports(user.getId());
        List<ReportRequest> reportsList = (reports).subList(0, Math.min(reports.size(), 5));
        return new SignInResponse(jwt, user, Optional.ofNullable(user.getManager()).map(User::getId).orElse(0L), data, reportsList);
    }
}