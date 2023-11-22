package TerraTech.BranchManagementBackend.controllers.userControllers;

import TerraTech.BranchManagementBackend.dto.auth.SignUpRequest;
import TerraTech.BranchManagementBackend.dto.data.user.UserResponse;
import TerraTech.BranchManagementBackend.dto.services.SearchEmployeeResponse;
import TerraTech.BranchManagementBackend.dto.data.user.UserRequest;
import TerraTech.BranchManagementBackend.services.jwtServices.JwtService;
import TerraTech.BranchManagementBackend.services.userServices.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/TerraTechInc")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;
    private final JwtService jwtService;

    @PostMapping("/manager/addEmployee")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public UserResponse addEmployee(@RequestBody SignUpRequest request, @RequestHeader("Authorization") String token) {
        return managerService.addEmployee(request, token);
    }

    @GetMapping("/manager/getEmployee/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public SearchEmployeeResponse getEmployee(@PathVariable Long id) {
        return managerService.searchEmployee(id);
    }


    @GetMapping("/manager/getEmployeesOfManager/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public List<UserResponse> getEmployeesOfManager(@PathVariable Long id) {
        return managerService.getManagerEmployees(id);
    }

    @DeleteMapping("/manager/deleteEmployee/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        return managerService.deleteEmployee(id);
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
