package TerraTech.BranchManagementBackend.services.dataServices;

import TerraTech.BranchManagementBackend.dto.data.report.ReportRequest;
import TerraTech.BranchManagementBackend.dto.data.report.ReportUpdateRequest;
import TerraTech.BranchManagementBackend.dto.data.report.ReportResponse;
import TerraTech.BranchManagementBackend.exceptions.data.product.ProductNotFoundException;
import TerraTech.BranchManagementBackend.exceptions.data.report.ReportNotFoundException;
import TerraTech.BranchManagementBackend.exceptions.manager.ManagerNotFoundException;
import TerraTech.BranchManagementBackend.models.Product;
import TerraTech.BranchManagementBackend.models.Report;
import TerraTech.BranchManagementBackend.repositories.ProductRepository;
import TerraTech.BranchManagementBackend.repositories.ReportRepository;
import TerraTech.BranchManagementBackend.repositories.UserRepository;
import TerraTech.BranchManagementBackend.services.jwtServices.JwtService;
import TerraTech.BranchManagementBackend.utils.ExtractToken;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final ProductRepository productRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public ReportResponse addReport(ReportRequest request, String token) {
        var tokenSubstring = ExtractToken.extractToken(token);
        var user = userRepository.findByEmail(jwtService.extractUserName(tokenSubstring)).orElseThrow(ManagerNotFoundException::new);
        var product = productRepository.findById(Long.parseLong(request.getProduct_id())).orElseThrow(ProductNotFoundException::new);
        var report = Report.builder().description(request.getDescription()).createDate(LocalDate.now()).peopleNotifiedAboutProduct(request.getPeopleNotifiedAboutProduct()).peopleSoldTo(request.getPeopleSoldTo()).user(user).product(product).build();
        report = reportRepository.save(report);
        return ReportResponse.builder().createDate(report.getCreateDate()).description(report.getDescription()).id(report.getId()).peopleNotifiedAboutProduct(report.getPeopleNotifiedAboutProduct()).peopleSoldTo(report.getPeopleSoldTo()).build();
    }

    public ReportResponse searchReport(Long id) {
        Report report = reportRepository.findById(id).orElseThrow(ReportNotFoundException::new);
        return ReportResponse.builder().createDate(report.getCreateDate()).peopleSoldTo(report.getPeopleSoldTo()).id(report.getId()).description(report.getDescription()).peopleNotifiedAboutProduct(report.getPeopleNotifiedAboutProduct()).build();
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

    public ReportUpdateRequest editReport(ReportUpdateRequest request, Long id) {
        return reportRepository.findById(id).map(report -> {
            request.getDescription().ifPresent(report::setDescription);
            request.getProductId().ifPresent(productId -> {
                Product product = productRepository.findById(Long.parseLong(request.getProductId().orElseThrow(ProductNotFoundException::new))).orElseThrow(ProductNotFoundException::new);
                report.setProduct(product);
            });
            request.getPeopleSoldTo().ifPresent(report::setPeopleSoldTo);
            request.getPeopleNotifiedAboutProduct().ifPresent(report::setPeopleNotifiedAboutProduct);
            reportRepository.save(report);
            return request;
        }).orElseThrow(ReportNotFoundException::new);
    }

    public List<ReportResponse> getEmployeesReports(Long id) {
        List<Report> reports = reportRepository.findByUserId(id);
        return reports.stream().map(report -> ReportResponse.builder().id(report.getId()).description(report.getDescription()).createDate(report.getCreateDate()).peopleSoldTo(report.getPeopleSoldTo()).peopleNotifiedAboutProduct(report.getPeopleNotifiedAboutProduct()).build()).collect(Collectors.toList());
    }

    public List<ReportResponse> getProductReports(Long id) {
        List<Report> reports = reportRepository.findByProductId(id);
        return reports.stream().map(report -> ReportResponse.builder().id(report.getId()).description(report.getDescription()).createDate(report.getCreateDate()).peopleSoldTo(report.getPeopleSoldTo()).peopleNotifiedAboutProduct(report.getPeopleNotifiedAboutProduct()).build()).collect(Collectors.toList());
    }
}
