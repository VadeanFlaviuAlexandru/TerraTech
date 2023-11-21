package TerraTech.BranchManagementBackend.controllers.dataControllers;

import TerraTech.BranchManagementBackend.dto.data.report.reportRequest;
import TerraTech.BranchManagementBackend.dto.data.report.reportUpdateRequest;
import TerraTech.BranchManagementBackend.models.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/TerraTechInc")
@RequiredArgsConstructor
public class reportController {

    private final TerraTech.BranchManagementBackend.services.dataServices.reportService reportService;

    @PostMapping("/report/addReport")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') || hasRole('ROLE_MANAGER')")
    public Report addReport(@RequestBody reportRequest request, @RequestHeader("Authorization") String token) {
        return reportService.addReport(request, token);
    }

    @GetMapping("/report/searchReport/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') || hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public Report searchReport(@PathVariable Long id) {
        return reportService.searchReport(id);
    }

    @DeleteMapping("/report/deleteReport/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') || hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteReport(@PathVariable Long id) {
        return reportService.deleteReport(id);
    }

    @PutMapping("/report/editReport/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') || hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public reportUpdateRequest editReport(@RequestBody reportUpdateRequest request, @PathVariable Long id) {
        return reportService.editReport(request, id);
    }

    @GetMapping("/report/getEmployeesReports/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public List<Report> getEmployeesReports(@PathVariable Long id) {
        return reportService.getEmployeesReports(id);
    }

    @GetMapping("/report/getProductsReports/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public List<Report> getProductsReports(@PathVariable Long id) {
        return reportService.getProductReports(id);
    }
}
