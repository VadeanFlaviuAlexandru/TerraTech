package TerraTech.BranchManagementBackend.services.data;

import TerraTech.BranchManagementBackend.dto.data.report.ReportRequest;
import TerraTech.BranchManagementBackend.dto.data.report.ReportResponse;
import TerraTech.BranchManagementBackend.dto.data.report.ReportUpdateRequest;
import TerraTech.BranchManagementBackend.exceptions.data.product.ProductNotFoundException;
import TerraTech.BranchManagementBackend.exceptions.data.report.ReportNotFoundException;
import TerraTech.BranchManagementBackend.models.Product;
import TerraTech.BranchManagementBackend.models.Report;
import TerraTech.BranchManagementBackend.models.User;
import TerraTech.BranchManagementBackend.repositories.ProductRepository;
import TerraTech.BranchManagementBackend.repositories.ReportRepository;
import TerraTech.BranchManagementBackend.services.user.ManagerService;
import lombok.RequiredArgsConstructor;
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
    private final ManagerService managerService;

    public ReportResponse addReport(ReportRequest request, String token) {
        User user = managerService.extractManager(token);
        Product product = productRepository.findById(Long.parseLong(request.getProductId()))
                .orElseThrow(ProductNotFoundException::new);
        Report report = Report.builder().description(request.getDescription()).createDate(LocalDate.now())
                .peopleNotified(request.getPeopleNotified()).peopleSold(request.getPeopleSold())
                .user(user).product(product)
                .build();
        reportRepository.save(report);
        return new ReportResponse(report.getId(), report.getDescription(), report.getCreateDate(),
                report.getPeopleNotified(), report.getPeopleSold(), product.getName());
    }

    public ReportResponse searchReport(long id) {
        Report report = reportRepository.findById(id).orElseThrow(ReportNotFoundException::new);
        return new ReportResponse(report.getId(), report.getDescription(), report.getCreateDate(),
                report.getPeopleNotified(), report.getPeopleSold(), report.getProduct().getName());
    }

    public long deleteReport(long id) {
        reportRepository.deleteById(id);
        return id;
    }

    public ReportUpdateRequest editReport(Report report, ReportUpdateRequest request, long id) {
        report.setDescription(Optional.ofNullable(request.getDescription()).orElse(report.getDescription()));
        Optional.ofNullable(request.getProductId()).map(productId -> productRepository
                        .findById(Long.parseLong(productId)).orElseThrow(ProductNotFoundException::new))
                .ifPresent(report::setProduct);
        report.setPeopleSold(Optional.ofNullable(request.getPeopleSold()).orElse(report.getPeopleSold()));
        report.setPeopleNotified(Optional.ofNullable(request.getPeopleNotified()).orElse(report.getPeopleNotified()));
        reportRepository.save(report);
        return request;
    }


    public List<ReportResponse> getEmployeesReports(long id) {
        List<Report> reports = reportRepository.findByUserId(id);
        return reports.stream().map(report -> new ReportResponse(report.getId(), report.getDescription(), report.getCreateDate(),
                report.getPeopleNotified(), report.getPeopleSold(), report.getProduct().getName())).collect(Collectors.toList());
    }

    public List<ReportResponse> getProductReports(long id) {
        List<Report> reports = reportRepository.findByProductId(id);
        return reports.stream().map(report -> new ReportResponse(report.getId(), report.getDescription(), report.getCreateDate(),
                report.getPeopleNotified(), report.getPeopleSold(), report.getProduct().getName())).collect(Collectors.toList());
    }
}
