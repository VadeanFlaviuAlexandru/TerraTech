package TerraTech.BranchManagementBackend.controllers.user;

import TerraTech.BranchManagementBackend.dto.auth.SignUpRequest;
import TerraTech.BranchManagementBackend.dto.data.user.UserResponse;
import TerraTech.BranchManagementBackend.dto.services.SearchEmployeeResponse;
import TerraTech.BranchManagementBackend.dto.data.user.UserRequest;
import TerraTech.BranchManagementBackend.exceptions.manager.EmployeeRegistrationException;
import TerraTech.BranchManagementBackend.exceptions.manager.NotFoundException;
import TerraTech.BranchManagementBackend.models.User;
import TerraTech.BranchManagementBackend.repositories.ReportRepository;
import TerraTech.BranchManagementBackend.repositories.UserRepository;
import TerraTech.BranchManagementBackend.services.jwt.JwtService;
import TerraTech.BranchManagementBackend.services.user.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/TerraTechInc")
@RequiredArgsConstructor
public class ManagerController {

    private final UserRepository userRepository;
    private final ManagerService managerService;

    @PostMapping("/manager/addEmployee")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public UserResponse addEmployee(@RequestBody SignUpRequest request, @RequestHeader("Authorization") String token) {
        userRepository.findByEmail(request.getEmail()).ifPresent(userFound -> {
            throw new EmployeeRegistrationException("An employee already has that email address!");
        });
        return managerService.addEmployee(request, token);
    }

    @GetMapping("/manager/getEmployee/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN') || hasRole('ROLE_EMPLOYEE')")
    public SearchEmployeeResponse getEmployee(@PathVariable long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        return managerService.searchEmployee(user, id);
    }


    @GetMapping("/manager/getEmployeesOfManager/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public List<UserResponse> getEmployeesOfManager(@PathVariable Long id) {
        List<User> users = userRepository.findByManagerId(id);
        return managerService.getManagerEmployees(users);
    }

    @DeleteMapping("/manager/deleteEmployee/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        Long deletedId = managerService.deleteEmployee(id);
        return ResponseEntity.ok("Employee with id: " + deletedId + "deleted successfully");
    }

    @PutMapping("/manager/editEmployee/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public UserResponse editEmployee(@RequestBody UserRequest request, @PathVariable Long id) {
        return managerService.editEmployee(request, id);
    }

    @PutMapping("/manager/changeStatus/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public UserResponse editEmployee(@PathVariable Long id) {
        return managerService.changeStatus(id);
    }
}
