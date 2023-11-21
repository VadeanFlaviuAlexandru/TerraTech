package TerraTech.BranchManagementBackend.controllers.userControllers;

import TerraTech.BranchManagementBackend.dto.data.user.managerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/TerraTechInc")
@RequiredArgsConstructor
public class adminController {

    private final TerraTech.BranchManagementBackend.services.userServices.managerService managerService;

    @GetMapping("/admin/getOnlyManagers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<managerRequest> getOnlyManagers() {
        return managerService.getAllManagers();
    }

}
