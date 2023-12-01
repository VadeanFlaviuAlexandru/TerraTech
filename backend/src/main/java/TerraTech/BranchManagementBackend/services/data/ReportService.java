package TerraTech.BranchManagementBackend.services.data;

import TerraTech.BranchManagementBackend.dto.data.report.ReportRequest;
import TerraTech.BranchManagementBackend.dto.data.report.ReportUpdateRequest;
import TerraTech.BranchManagementBackend.dto.data.report.ReportResponse;
import TerraTech.BranchManagementBackend.exceptions.data.product.ProductNotFoundException;
import TerraTech.BranchManagementBackend.exceptions.data.report.ReportNotFoundException;
import TerraTech.BranchManagementBackend.exceptions.manager.ManagerNotFoundException;
import TerraTech.BranchManagementBackend.models.Report;
import TerraTech.BranchManagementBackend.repositories.ProductRepository;
import TerraTech.BranchManagementBackend.repositories.ReportRepository;
import TerraTech.BranchManagementBackend.repositories.UserRepository;
import TerraTech.BranchManagementBackend.services.jwt.JwtService;
import TerraTech.BranchManagementBackend.utils.ExtractToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public ReportResponse addReport(ReportRequest request, String token) {
        var tokenSubstring = ExtractToken.extractToken(token);
        var user = userRepository.findByEmail(jwtService.extractUserName(tokenSubstring)).orElseThrow(ManagerNotFoundException::new);
        var product = productRepository.findById(Long.parseLong(request.getProductId())).orElseThrow(ProductNotFoundException::new);
        var report = Report.builder().description(request.getDescription()).createDate(LocalDate.now()).peopleNotified(request.getPeopleNotified()).peopleSold(request.getPeopleSold()).user(user).product(product).build();
        report = reportRepository.save(report);
        return ReportResponse.builder().createDate(report.getCreateDate()).description(report.getDescription()).productName(product.getName()).id(product.getId()).peopleNotified(report.getPeopleNotified()).peopleSold(report.getPeopleSold()).build();
    }

    public ReportResponse searchReport(Long id) {
        Report report = reportRepository.findById(id).orElseThrow(ReportNotFoundException::new);
        return ReportResponse.builder().productName(report.getProduct().getName()).createDate(report.getCreateDate()).peopleSold(report.getPeopleSold()).id(report.getId()).description(report.getDescription()).peopleNotified(report.getPeopleNotified()).build();
    }

    public ResponseEntity<?> deleteReport(Long id) {
        reportRepository.findById(id).orElseThrow(ReportNotFoundException::new);
        reportRepository.deleteById(id);
        return ResponseEntity.ok("Report registered successfully");
    }

    public ReportUpdateRequest editReport(ReportUpdateRequest request, Long id) {
        Report report = reportRepository.findById(id).orElseThrow(ReportNotFoundException::new);
        report.setDescription(Optional.ofNullable(request.getDescription()).orElse(report.getDescription()));
        Optional.ofNullable(request.getProductId()).map(productId -> productRepository.findById(Long.parseLong(productId)).orElseThrow(ProductNotFoundException::new)).ifPresent(report::setProduct);
        report.setPeopleSold(Optional.ofNullable(request.getPeopleSold()).orElse(report.getPeopleSold()));
        report.setPeopleNotified(Optional.ofNullable(request.getPeopleNotified()).orElse(report.getPeopleNotified()));

        reportRepository.save(report);

        return request;
    }


    public List<ReportResponse> getEmployeesReports(Long id) {
        List<Report> reports = reportRepository.findByUserId(id);
        return reports.stream().map(report -> ReportResponse.builder().productName(report.getProduct().getName()).id(report.getId()).description(report.getDescription()).createDate(report.getCreateDate()).peopleSold(report.getPeopleSold()).peopleNotified(report.getPeopleNotified()).build()).collect(Collectors.toList());
    }

    public List<ReportResponse> getProductReports(Long id) {
        List<Report> reports = reportRepository.findByProductId(id);
        return reports.stream().map(report -> ReportResponse.builder().productName(report.getProduct().getName()).id(report.getId()).description(report.getDescription()).createDate(report.getCreateDate()).peopleSold(report.getPeopleSold()).peopleNotified(report.getPeopleNotified()).build()).collect(Collectors.toList());
    }
}
