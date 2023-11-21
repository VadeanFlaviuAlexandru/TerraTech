package TerraTech.BranchManagementBackend.controllers.userControllers;

import TerraTech.BranchManagementBackend.dto.auth.signUpRequest;
import TerraTech.BranchManagementBackend.dto.data.user.managerRequest;
import TerraTech.BranchManagementBackend.dto.services.searchEmployeeRequest;
import TerraTech.BranchManagementBackend.dto.data.user.userRequest;
import TerraTech.BranchManagementBackend.models.User;
import TerraTech.BranchManagementBackend.services.jwtServices.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/TerraTechInc")
@RequiredArgsConstructor
public class managerController {

    private final TerraTech.BranchManagementBackend.services.userServices.managerService managerService;
    private final JwtService jwtService;

    @PostMapping("/manager/addEmployee")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public User addEmployee(@RequestBody signUpRequest request, @RequestHeader("Authorization") String token) {
        return managerService.addEmployee(request, token);
    }

    @GetMapping("/manager/getEmployee/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public searchEmployeeRequest getEmployee(@PathVariable Long id) {
        return managerService.searchEmployee(id);
    }


    @GetMapping("/manager/getEmployeesOfManager/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public List<User> getEmployeesOfManager(@PathVariable Long id) {
        return managerService.getManagerEmployees(id);
    }

    @DeleteMapping("/manager/deleteEmployee/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        return managerService.deleteEmployee(id);
    }

    @PutMapping("/manager/editEmployee/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public User editEmployee(@RequestBody userRequest request, @PathVariable Long id) {
        return managerService.editEmployee(request, id);
    }

    @PutMapping("/manager/changeStatus/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public User editEmployee(@PathVariable Long id) {
        return managerService.changeStatus(id);
    }
}
