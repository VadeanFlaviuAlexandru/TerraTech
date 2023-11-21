package TerraTech.BranchManagementBackend.services.authServices;

import TerraTech.BranchManagementBackend.dto.auth.signInResponse;
import TerraTech.BranchManagementBackend.dto.auth.signInRequest;
import TerraTech.BranchManagementBackend.dto.auth.signUpRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.chartRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.dataKeyRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.dataRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.reportRequest;
import TerraTech.BranchManagementBackend.enums.Role;
import TerraTech.BranchManagementBackend.exceptions.auth.authenticateException;
import TerraTech.BranchManagementBackend.exceptions.manager.registerException;
import TerraTech.BranchManagementBackend.models.User;
import TerraTech.BranchManagementBackend.repositories.ReportRepository;
import TerraTech.BranchManagementBackend.repositories.UserRepository;
import TerraTech.BranchManagementBackend.services.jwtServices.JwtService;
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
public class authenticationService {

    private final UserRepository userRepository;
    private final TerraTech.BranchManagementBackend.services.userServices.userService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ReportRepository reportRepository;

    public ResponseEntity<?> signup(signUpRequest request) {
        var search = userRepository.findByEmail(request.getEmail());
        if (search.isPresent()) {
            throw new registerException("This email already exists!");

        }

        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName()).email(request.getEmail()).phone(request.getPhone()).password(passwordEncoder.encode(request.getPassword())).role(Role.ROLE_MANAGER).createdAt(LocalDate.now()).status(true).build();

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    public signInResponse signin(signInRequest request) {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new authenticateException("No account with this email found!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new authenticateException("Wrong password!");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var jwt = jwtService.generateToken(user);
//        return jwtAuthenticationResponse.builder().token(jwt).build();

        List<dataKeyRequest> dataKeyRequestList = List.of(dataKeyRequest.builder().name("peopleNotifiedAboutProduct").build(), dataKeyRequest.builder().name("peopleSoldTo").build());
        List<dataRequest> dataList = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            LocalDate currentMonth = LocalDate.of(LocalDate.now().getYear(), month, 1);
            Long totalNotifiedPeopleSpecificMonth = reportRepository.userPeopleSoldToSpecificMonth(currentMonth.getMonthValue(), currentMonth.getYear(), user.getId());
            Long totalSoldPeopleSpecificMonth = reportRepository.userNotifiedPeopleSpecificMonth(currentMonth.getMonthValue(), currentMonth.getYear(), user.getId());
            dataRequest monthReport = dataRequest.builder().name(currentMonth.getMonth().name()).peopleSoldTo(totalSoldPeopleSpecificMonth).peopleNotifiedAboutProduct(totalNotifiedPeopleSpecificMonth).build();
            dataList.add(monthReport);
        }
        List<reportRequest> reportsList = (reportRepository.findReports(user.getId())).subList(0, Math.min(reportRepository.findReports(user.getId()).size(), 5));
        chartRequest info = chartRequest.builder().data(dataList).dataKeys(dataKeyRequestList.subList(0, Math.min(5, dataKeyRequestList.size()))).build();
        return new signInResponse(jwt, user, info, reportsList);
    }
}