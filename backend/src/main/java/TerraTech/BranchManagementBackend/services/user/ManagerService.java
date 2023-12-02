package TerraTech.BranchManagementBackend.services.user;

import TerraTech.BranchManagementBackend.dto.auth.SignUpRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.ChartRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.DataKeyRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.DataRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.ReportRequest;
import TerraTech.BranchManagementBackend.dto.data.user.ManagerRequest;
import TerraTech.BranchManagementBackend.dto.data.user.UserRequest;
import TerraTech.BranchManagementBackend.dto.data.user.UserResponse;
import TerraTech.BranchManagementBackend.dto.services.SearchEmployeeResponse;
import TerraTech.BranchManagementBackend.enums.Role;
import TerraTech.BranchManagementBackend.exceptions.manager.EmployeeRegistrationException;
import TerraTech.BranchManagementBackend.exceptions.manager.ManagerNotFoundException;
import TerraTech.BranchManagementBackend.exceptions.manager.NotFoundException;
import TerraTech.BranchManagementBackend.models.Report;
import TerraTech.BranchManagementBackend.models.User;
import TerraTech.BranchManagementBackend.repositories.ReportRepository;
import TerraTech.BranchManagementBackend.repositories.UserRepository;
import TerraTech.BranchManagementBackend.services.jwt.JwtService;
import TerraTech.BranchManagementBackend.utils.ExtractToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ReportRepository reportRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public User extractManager(String token) {
        String tokenSubstring = ExtractToken.extractToken(token);
        String username = jwtService.extractUserName(tokenSubstring);
        return userRepository.findByEmail(username).orElseThrow(ManagerNotFoundException::new);
    }

    public List<DataKeyRequest> dataKey() {
        return List.of(DataKeyRequest.builder().name("peopleNotified").build(),
                DataKeyRequest.builder().name("peopleSold").build());
    }

    public ChartRequest chartInfo(long id) {
        List<DataRequest> data = new ArrayList<>();
        List<Object[]> monthlyReport = reportRepository.userMonthlyReport(LocalDate.now().getYear(), id);
        for (Object[] report : monthlyReport) {
            int month = (int) report[0];
            Long notifiedCount = (Long) report[1];
            Long soldCount = (Long) report[2];
            DataRequest monthReport = DataRequest.builder().name(Month.of(month).name())
                    .peopleSold(soldCount).peopleNotified(notifiedCount).build();
            data.add(monthReport);
        }
        return new ChartRequest(data, dataKey());
    }

    public UserResponse addEmployee(SignUpRequest request, String token) {
        User manager = extractManager(token);
        User user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .status(true).phone(request.getPhone()).email(request.getEmail()).createdAt(LocalDate.now())
                .password(passwordEncoder.encode(request.getPassword())).manager(manager).role(Role.ROLE_EMPLOYEE)
                .build();
        userRepository.save(user);
        return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getPhone(),
                user.isStatus(), user.getEmail(), user.getCreatedAt(), user.getRole());
    }

    public SearchEmployeeResponse searchEmployee(User user, long id) {
        List<User> employees = userRepository.findEmployeesByManagerId(id);
        List<ReportRequest> reports = reportRepository.findReports((id));
        List<ReportRequest> latestReports = reports.subList(0, Math.min(reports.size(), 5));
        return new SearchEmployeeResponse(user, employees, chartInfo(id), latestReports);
    }

    public List<ManagerRequest> getAllManagers() {
        List<ManagerRequest> managers = new ArrayList<>();
        for (User manager : userRepository.getAllManagers()) {
            ManagerRequest currentManager = new ManagerRequest(manager.getId(), manager.getFirstName(),
                    manager.getLastName(), manager.getEmail(), manager.getPhone(), manager.getCreatedAt(),
                    manager.isStatus());
            managers.add(currentManager);
        }
        return managers;
    }

    public long deleteEmployee(long id) {
        List<Report> userReports = reportRepository.findByUserId(id);
        for (Report report : userReports) {
            report.setUser(null);
        }
        userRepository.deleteById(id);
        return id;
    }

    public UserResponse editEmployee(UserRequest request, long id) {
        User userRequest = userRepository.findById(id).map(user -> {
            user.setEmail(Optional.ofNullable(request.getEmail()).orElse(user.getEmail()));
            user.setRole(Optional.ofNullable(request.getRole()).orElse(user.getRole()));
            user.setFirstName(Optional.ofNullable(request.getFirstName()).orElse(user.getFirstName()));
            user.setLastName(Optional.ofNullable(request.getLastName()).orElse(user.getLastName()));
            user.setPhone(Optional.ofNullable(request.getPhone()).orElse(user.getPhone()));
            return userRepository.save(user);
        }).orElseThrow(() -> new NotFoundException(id));
        return new UserResponse(userRequest.getId(), userRequest.getFirstName(), userRequest.getLastName(),
                userRequest.getPhone(), userRequest.isStatus(), userRequest.getEmail(), userRequest.getCreatedAt(),
                userRequest.getRole());
    }

    public UserResponse changeStatus(long id) {
        User userRequest = userRepository.findById(id).map(user -> {
            user.setStatus(!user.isStatus());
            return userRepository.save(user);
        }).orElseThrow(() -> new NotFoundException(id));
        return new UserResponse(userRequest.getId(), userRequest.getFirstName(), userRequest.getLastName(),
                userRequest.getPhone(), userRequest.isStatus(), userRequest.getEmail(), userRequest.getCreatedAt(),
                userRequest.getRole());
    }

    public List<UserResponse> getManagerEmployees(List<User> users) {
        return users.stream().map(user -> new UserResponse(user.getId(), user.getFirstName(), user.getLastName(),
                        user.getPhone(), user.isStatus(), user.getEmail(), user.getCreatedAt(), user.getRole()))
                .collect(Collectors.toList());
    }
}
