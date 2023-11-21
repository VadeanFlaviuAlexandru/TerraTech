package TerraTech.BranchManagementBackend.services.userServices;

import TerraTech.BranchManagementBackend.dto.auth.signUpRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.chartRequest;
import TerraTech.BranchManagementBackend.dto.data.user.managerRequest;
import TerraTech.BranchManagementBackend.dto.services.searchEmployeeRequest;
import TerraTech.BranchManagementBackend.dto.data.user.userRequest;
import TerraTech.BranchManagementBackend.enums.Role;
import TerraTech.BranchManagementBackend.exceptions.manager.employeeRegistrationException;
import TerraTech.BranchManagementBackend.exceptions.manager.managerNotFoundException;
import TerraTech.BranchManagementBackend.exceptions.manager.notFoundException;
import TerraTech.BranchManagementBackend.models.Report;
import TerraTech.BranchManagementBackend.models.User;
import TerraTech.BranchManagementBackend.repositories.ReportRepository;
import TerraTech.BranchManagementBackend.repositories.UserRepository;
import TerraTech.BranchManagementBackend.services.jwtServices.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import TerraTech.BranchManagementBackend.dto.chart.user.dataRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.dataKeyRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.reportRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class managerService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ReportRepository reportRepository;


    //to-do: remake this function
    public User addEmployee(signUpRequest request, String token) {
        String tokenSubstring = token.substring(7);
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new employeeRegistrationException("An employee already has that email address!");
        }
        User manager = userRepository.findByEmail(jwtService.extractUserName(tokenSubstring)).orElseThrow(managerNotFoundException::new);
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new employeeRegistrationException("An employee already has that email address!");
        }
        User user = createUser(request, manager);
        user = userRepository.save(user);
        return user;
    }

    private User createUser(signUpRequest request, User manager) {
        return User.builder().firstName(request.getFirstName()).lastName(request.getLastName()).status(true).phone(request.getPhone()).email(request.getEmail()).createdAt(LocalDate.now()).password(passwordEncoder.encode(request.getPassword())).manager(manager).role(Role.ROLE_EMPLOYEE).build();
    }

    public searchEmployeeRequest searchEmployee(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new notFoundException(id));
        if (user != null) {
            List<User> employeeList = userRepository.findEmployeesByManagerId(id);
            List<dataKeyRequest> dataKeyRequestList = List.of(dataKeyRequest.builder().name("peopleNotifiedAboutProduct").build(), dataKeyRequest.builder().name("peopleSoldTo").build());
            List<dataRequest> dataList = new ArrayList<>();
            for (int month = 1; month <= 12; month++) {
                LocalDate currentMonth = LocalDate.of(LocalDate.now().getYear(), month, 1);
                Long totalNotifiedPeopleSpecificMonth = reportRepository.userPeopleSoldToSpecificMonth(currentMonth.getMonthValue(), currentMonth.getYear(), id);
                Long totalSoldPeopleSpecificMonth = reportRepository.userNotifiedPeopleSpecificMonth(currentMonth.getMonthValue(), currentMonth.getYear(), id);
                dataRequest monthReport = dataRequest.builder().name(currentMonth.getMonth().name()).peopleSoldTo(totalSoldPeopleSpecificMonth).peopleNotifiedAboutProduct(totalNotifiedPeopleSpecificMonth).build();
                dataList.add(monthReport);
            }
            List<reportRequest> reportsList = (reportRepository.findReports(id)).subList(0, Math.min(reportRepository.findReports(id).size(), 5));
            chartRequest info = chartRequest.builder().data(dataList).dataKeys(dataKeyRequestList).build();
            return new searchEmployeeRequest(user, employeeList, info, reportsList);
        } else {
            return null;
        }
    }

    public List<managerRequest> getAllManagers() {
        List<managerRequest> listForManagers = new ArrayList<>();

        for (User manager : userRepository.getAllManagers()) {
            managerRequest currentManager = managerRequest.builder().id(manager.getId()).firstName(manager.getFirstName()).lastName(manager.getLastName()).email(manager.getEmail()).phone(manager.getPhone()).createdAt(manager.getCreatedAt()).status(manager.getStatus()).build();
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

    public User editEmployee(userRequest request, Long id) {
        return userRepository.findById(id).map(user -> {
            request.getEmail().ifPresent(user::setEmail);
            request.getRole().ifPresent(user::setRole);
            request.getFirstName().ifPresent(user::setFirstName);
            request.getLastName().ifPresent(user::setLastName);
            request.getPhone().ifPresent(user::setPhone);
            return userRepository.save(user);
        }).orElseThrow(() -> new notFoundException(id));
    }

    public User changeStatus(Long id) {
        return userRepository.findById(id).map(user -> {
            user.setStatus(!user.getStatus());
            return userRepository.save(user);
        }).orElseThrow(() -> new notFoundException(id));
    }


    public List<User> getManagerEmployees(Long id) {
        return userRepository.findByManagerId(id);
    }


}
