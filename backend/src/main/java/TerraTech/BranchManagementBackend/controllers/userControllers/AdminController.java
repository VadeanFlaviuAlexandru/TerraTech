package TerraTech.BranchManagementBackend.controllers.userControllers;

import TerraTech.BranchManagementBackend.dto.data.user.ManagerRequest;
import TerraTech.BranchManagementBackend.services.userServices.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/TerraTechInc")
@RequiredArgsConstructor
public class AdminController {

    private final ManagerService managerService;

    @GetMapping("/admin/getOnlyManagers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<ManagerRequest> getOnlyManagers() {
        return managerService.getAllManagers();
    }

}
