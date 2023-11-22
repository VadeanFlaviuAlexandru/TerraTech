package TerraTech.BranchManagementBackend.services.authServices;

import TerraTech.BranchManagementBackend.dto.auth.SignInResponse;
import TerraTech.BranchManagementBackend.dto.auth.SignInRequest;
import TerraTech.BranchManagementBackend.dto.auth.SignUpRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.ChartRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.DataKeyRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.DataRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.ReportRequest;
import TerraTech.BranchManagementBackend.enums.Role;
import TerraTech.BranchManagementBackend.exceptions.auth.AuthenticateException;
import TerraTech.BranchManagementBackend.exceptions.manager.RegisterException;
import TerraTech.BranchManagementBackend.models.User;
import TerraTech.BranchManagementBackend.repositories.ReportRepository;
import TerraTech.BranchManagementBackend.repositories.UserRepository;
import TerraTech.BranchManagementBackend.services.jwtServices.JwtService;
import TerraTech.BranchManagementBackend.services.userServices.UserService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ReportRepository reportRepository;

    public ResponseEntity<?> signup(SignUpRequest request) {

        userRepository.findByEmail(request.getEmail()).orElseThrow(()->new RegisterException("This email already exists!"));
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName()).email(request.getEmail()).phone(request.getPhone()).password(passwordEncoder.encode(request.getPassword())).role(Role.ROLE_MANAGER).createdAt(LocalDate.now()).status(true).build();
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    public SignInResponse signin(SignInRequest request) {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new AuthenticateException("No account with this email found!"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthenticateException("Wrong password!");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var jwt = jwtService.generateToken(user);
//        return jwtAuthenticationResponse.builder().token(jwt).build();

        List<DataKeyRequest> dataKeyRequestList = List.of(DataKeyRequest.builder().name("peopleNotifiedAboutProduct").build(), DataKeyRequest.builder().name("peopleSoldTo").build());
        List<DataRequest> dataList = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            LocalDate currentMonth = LocalDate.of(LocalDate.now().getYear(), month, 1);
            Long totalNotifiedPeopleSpecificMonth = reportRepository.userPeopleSoldToSpecificMonth(currentMonth.getMonthValue(), currentMonth.getYear(), user.getId());
            Long totalSoldPeopleSpecificMonth = reportRepository.userNotifiedPeopleSpecificMonth(currentMonth.getMonthValue(), currentMonth.getYear(), user.getId());
            DataRequest monthReport = DataRequest.builder().name(currentMonth.getMonth().name()).peopleSoldTo(totalSoldPeopleSpecificMonth).peopleNotifiedAboutProduct(totalNotifiedPeopleSpecificMonth).build();
            dataList.add(monthReport);
        }

        List<ReportRequest> reportsList = (reportRepository.findReports(user.getId())).subList(0, Math.min(reportRepository.findReports(user.getId()).size(), 5));
        ChartRequest info = ChartRequest.builder().data(dataList).dataKeys(dataKeyRequestList.subList(0, Math.min(5, dataKeyRequestList.size()))).build();

        return new SignInResponse(jwt, user, info, reportsList);
    }
}