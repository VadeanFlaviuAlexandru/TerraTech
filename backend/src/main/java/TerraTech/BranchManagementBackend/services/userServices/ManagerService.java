package TerraTech.BranchManagementBackend.services.userServices;

import TerraTech.BranchManagementBackend.dto.auth.SignUpRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.ChartRequest;
import TerraTech.BranchManagementBackend.dto.data.user.ManagerRequest;
import TerraTech.BranchManagementBackend.dto.data.user.UserResponse;
import TerraTech.BranchManagementBackend.dto.services.SearchEmployeeResponse;
import TerraTech.BranchManagementBackend.dto.data.user.UserRequest;
import TerraTech.BranchManagementBackend.enums.Role;
import TerraTech.BranchManagementBackend.exceptions.manager.EmployeeRegistrationException;
import TerraTech.BranchManagementBackend.exceptions.manager.ManagerNotFoundException;
import TerraTech.BranchManagementBackend.exceptions.manager.NotFoundException;
import TerraTech.BranchManagementBackend.models.Report;
import TerraTech.BranchManagementBackend.models.User;
import TerraTech.BranchManagementBackend.repositories.ReportRepository;
import TerraTech.BranchManagementBackend.repositories.UserRepository;
import TerraTech.BranchManagementBackend.services.jwtServices.JwtService;
import TerraTech.BranchManagementBackend.utils.ExtractToken;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import TerraTech.BranchManagementBackend.dto.chart.user.DataRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.DataKeyRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.ReportRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ReportRepository reportRepository;


    public UserResponse addEmployee(SignUpRequest request, String token) {
        String tokenSubstring = ExtractToken.extractToken(token);
        userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new EmployeeRegistrationException("An employee already has that email address!"));
        User manager = userRepository.findByEmail(jwtService.extractUserName(tokenSubstring)).orElseThrow(ManagerNotFoundException::new);
        User user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName()).status(true).phone(request.getPhone()).email(request.getEmail()).createdAt(LocalDate.now()).password(passwordEncoder.encode(request.getPassword())).manager(manager).role(Role.ROLE_EMPLOYEE).build();
        userRepository.save(user);
        return UserResponse.builder().firstName(user.getFirstName()).lastName(user.getLastName()).status(user.getStatus()).email(user.getEmail()).createdAt(user.getCreatedAt()).role(user.getRole()).build();
    }

    public SearchEmployeeResponse searchEmployee(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        if (user != null) {
            List<User> employeeList = userRepository.findEmployeesByManagerId(id);
            List<DataKeyRequest> dataKeyRequestList = List.of(DataKeyRequest.builder().name("peopleNotifiedAboutProduct").build(), DataKeyRequest.builder().name("peopleSoldTo").build());
            List<DataRequest> dataList = new ArrayList<>();
            for (int month = 1; month <= 12; month++) {
                LocalDate currentMonth = LocalDate.of(LocalDate.now().getYear(), month, 1);
                Long totalNotifiedPeopleSpecificMonth = reportRepository.userPeopleSoldToSpecificMonth(currentMonth.getMonthValue(), currentMonth.getYear(), id);
                Long totalSoldPeopleSpecificMonth = reportRepository.userNotifiedPeopleSpecificMonth(currentMonth.getMonthValue(), currentMonth.getYear(), id);
                DataRequest monthReport = DataRequest.builder().name(currentMonth.getMonth().name()).peopleSoldTo(totalSoldPeopleSpecificMonth).peopleNotifiedAboutProduct(totalNotifiedPeopleSpecificMonth).build();
                dataList.add(monthReport);
            }
            List<ReportRequest> reportsList = (reportRepository.findReports(id)).subList(0, Math.min(reportRepository.findReports(id).size(), 5));
            ChartRequest info = ChartRequest.builder().data(dataList).dataKeys(dataKeyRequestList).build();
            return new SearchEmployeeResponse(user, employeeList, info, reportsList);
        } else {
            return null;
        }
    }

    public List<ManagerRequest> getAllManagers() {
        List<ManagerRequest> listForManagers = new ArrayList<>();

        for (User manager : userRepository.getAllManagers()) {
            ManagerRequest currentManager = ManagerRequest.builder().id(manager.getId()).firstName(manager.getFirstName()).lastName(manager.getLastName()).email(manager.getEmail()).phone(manager.getPhone()).createdAt(manager.getCreatedAt()).status(manager.getStatus()).build();
            listForManagers.add(currentManager);
        }

        return listForManagers;
    }

    public ResponseEntity<String> deleteEmployee(Long id) {
        try {
            List<Report> userReports = reportRepository.findByUserId(id);
            for (Report report : userReports) {
                report.setUser(null);
            }
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Employee deleted successfully!");
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    public UserResponse editEmployee(UserRequest request, Long id) {
        User userRequest = userRepository.findById(id).map(user -> {
            request.getEmail().ifPresent(user::setEmail);
            request.getRole().ifPresent(user::setRole);
            request.getFirstName().ifPresent(user::setFirstName);
            request.getLastName().ifPresent(user::setLastName);
            request.getPhone().ifPresent(user::setPhone);
            return userRepository.save(user);
        }).orElseThrow(() -> new NotFoundException(id));
        return UserResponse.builder().id(userRequest.getId()).role(userRequest.getRole()).createdAt(userRequest.getCreatedAt()).email(userRequest.getEmail()).status(userRequest.getStatus()).lastName(userRequest.getLastName()).firstName(userRequest.getFirstName()).phone(userRequest.getPhone()).build();
    }

    public UserResponse changeStatus(Long id) {
        User userRequest = userRepository.findById(id).map(user -> {
            user.setStatus(!user.getStatus());
            return userRepository.save(user);
        }).orElseThrow(() -> new NotFoundException(id));
        return UserResponse.builder().id(userRequest.getId()).role(userRequest.getRole()).createdAt(userRequest.getCreatedAt()).email(userRequest.getEmail()).status(userRequest.getStatus()).lastName(userRequest.getLastName()).firstName(userRequest.getFirstName()).phone(userRequest.getPhone()).build();
    }


    public List<UserResponse> getManagerEmployees(Long id) {
        List<User> users = userRepository.findByManagerId(id);
        return users.stream().map(user -> UserResponse.builder().firstName(user.getFirstName()).lastName(user.getLastName()).status(user.getStatus()).email(user.getEmail()).createdAt(user.getCreatedAt()).role(user.getRole()).build()).collect(Collectors.toList());
    }
}
