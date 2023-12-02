package TerraTech.BranchManagementBackend.controllers.data;

import TerraTech.BranchManagementBackend.dto.data.report.ReportRequest;
import TerraTech.BranchManagementBackend.dto.data.report.ReportUpdateRequest;
import TerraTech.BranchManagementBackend.dto.data.report.ReportResponse;
import TerraTech.BranchManagementBackend.exceptions.data.report.ReportNotFoundException;
import TerraTech.BranchManagementBackend.models.Report;
import TerraTech.BranchManagementBackend.repositories.ReportRepository;
import TerraTech.BranchManagementBackend.services.data.ReportService;
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
    private final ReportRepository reportRepository;

    @PostMapping("/report/addReport")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') || hasRole('ROLE_MANAGER')")
    public ReportResponse addReport(@RequestBody ReportRequest request, @RequestHeader("Authorization") String token) {
        return reportService.addReport(request, token);
    }

    @GetMapping("/report/searchReport/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') || hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public ReportResponse searchReport(@PathVariable long id) {
        return reportService.searchReport(id);
    }

    @DeleteMapping("/report/deleteReport/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') || hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteReport(@PathVariable long id) {
        reportRepository.findById(id).orElseThrow(ReportNotFoundException::new);
        reportService.deleteReport(id);
        return ResponseEntity.ok("Employee with id: " + id + "deleted successfully");

    }

    @PutMapping("/report/editReport/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') || hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public ReportUpdateRequest editReport(@RequestBody ReportUpdateRequest request, @PathVariable long id) {
        Report report = reportRepository.findById(id).orElseThrow(ReportNotFoundException::new);
        return reportService.editReport(report, request, id);
    }

    @GetMapping("/report/getEmployeesReports/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public List<ReportResponse> getEmployeesReports(@PathVariable long id) {
        return reportService.getEmployeesReports(id);
    }

    @GetMapping("/report/getProductsReports/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public List<ReportResponse> getProductsReports(@PathVariable long id) {
        return reportService.getProductReports(id);
    }
}
