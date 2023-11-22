package TerraTech.BranchManagementBackend.controllers.dataControllers;

import TerraTech.BranchManagementBackend.dto.data.report.ReportRequest;
import TerraTech.BranchManagementBackend.dto.data.report.ReportUpdateRequest;
import TerraTech.BranchManagementBackend.dto.data.report.ReportResponse;
import TerraTech.BranchManagementBackend.services.dataServices.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/TerraTechInc")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/report/addReport")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') || hasRole('ROLE_MANAGER')")
    public ReportResponse addReport(@RequestBody ReportRequest request, @RequestHeader("Authorization") String token) {
        return reportService.addReport(request, token);
    }

    @GetMapping("/report/searchReport/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') || hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public ReportResponse searchReport(@PathVariable Long id) {
        return reportService.searchReport(id);
    }

    @DeleteMapping("/report/deleteReport/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') || hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteReport(@PathVariable Long id) {
        return reportService.deleteReport(id);
    }

    @PutMapping("/report/editReport/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') || hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public ReportUpdateRequest editReport(@RequestBody ReportUpdateRequest request, @PathVariable Long id) {
        return reportService.editReport(request, id);
    }

    @GetMapping("/report/getEmployeesReports/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public List<ReportResponse> getEmployeesReports(@PathVariable Long id) {
        return reportService.getEmployeesReports(id);
    }

    @GetMapping("/report/getProductsReports/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public List<ReportResponse> getProductsReports(@PathVariable Long id) {
        return reportService.getProductReports(id);
    }
}
