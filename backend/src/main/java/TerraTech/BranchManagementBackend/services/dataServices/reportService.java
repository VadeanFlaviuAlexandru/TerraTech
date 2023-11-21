package TerraTech.BranchManagementBackend.services.dataServices;

import TerraTech.BranchManagementBackend.dto.data.report.reportRequest;
import TerraTech.BranchManagementBackend.dto.data.report.reportUpdateRequest;
import TerraTech.BranchManagementBackend.exceptions.data.product.productNotFoundException;
import TerraTech.BranchManagementBackend.exceptions.data.report.reportNotFoundException;
import TerraTech.BranchManagementBackend.exceptions.manager.managerNotFoundException;
import TerraTech.BranchManagementBackend.models.Product;
import TerraTech.BranchManagementBackend.models.Report;
import TerraTech.BranchManagementBackend.repositories.ProductRepository;
import TerraTech.BranchManagementBackend.repositories.ReportRepository;
import TerraTech.BranchManagementBackend.repositories.UserRepository;
import TerraTech.BranchManagementBackend.services.jwtServices.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class reportService {

    private final ReportRepository reportRepository;
    private final ProductRepository productRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public Report addReport(reportRequest request, String token) {
        var tokenSubstring = token.substring(7);
        var report = Report.builder().description(request.getDescription()).createDate(LocalDate.now()).peopleNotifiedAboutProduct(request.getPeopleNotifiedAboutProduct()).peopleSoldTo(request.getPeopleSoldTo()).user(userRepository.findByEmail(jwtService.extractUserName(tokenSubstring)).orElseThrow(managerNotFoundException::new)).product(productRepository.findById(Long.parseLong(request.getProduct_id())).orElseThrow(productNotFoundException::new)).build();

        report = reportRepository.save(report);
        return report;
    }

    public Report searchReport(Long id) {
        return reportRepository.findById(id).orElseThrow(reportNotFoundException::new);
    }

    public ResponseEntity<String> deleteReport(Long id) {
        try {
            reportRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Employee deleted successfully!");
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    public reportUpdateRequest editReport(reportUpdateRequest request, Long id) {
        return reportRepository.findById(id).map(report -> {
            request.getDescription().ifPresent(report::setDescription);
            request.getProductId().ifPresent(productId -> {
                Product product = productRepository.findById(Long.parseLong(request.getProductId().orElseThrow(productNotFoundException::new))).orElseThrow(productNotFoundException::new);
                report.setProduct(product);
            });
            request.getPeopleSoldTo().ifPresent(report::setPeopleSoldTo);
            request.getPeopleNotifiedAboutProduct().ifPresent(report::setPeopleNotifiedAboutProduct);
            reportRepository.save(report);
            return request;
        }).orElseThrow(reportNotFoundException::new);
    }

    public List<Report> getEmployeesReports(Long id) {
        return reportRepository.findByUserId(id);
    }

    public List<Report> getProductReports(Long id) {
        return reportRepository.findByProductId(id);
    }
}
