package TerraTech.BranchManagementBackend.services.data;

import TerraTech.BranchManagementBackend.dto.chart.user.ChartRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.DataKeyRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.DataRequest;
import TerraTech.BranchManagementBackend.dto.data.product.ProductChartResponse;
import TerraTech.BranchManagementBackend.dto.data.product.ProductRequest;
import TerraTech.BranchManagementBackend.dto.data.product.ProductResponse;
import TerraTech.BranchManagementBackend.dto.data.product.ProductUpdateRequest;
import TerraTech.BranchManagementBackend.exceptions.data.product.ProductNotFoundException;
import TerraTech.BranchManagementBackend.models.Product;
import TerraTech.BranchManagementBackend.models.Report;
import TerraTech.BranchManagementBackend.models.User;
import TerraTech.BranchManagementBackend.repositories.ProductRepository;
import TerraTech.BranchManagementBackend.repositories.ReportRepository;
import TerraTech.BranchManagementBackend.services.user.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ReportRepository reportRepository;
    private final ManagerService managerService;

    public List<DataRequest> chartInfo(long id) {
        List<DataRequest> data = new ArrayList<>();
        List<Object[]> monthlyReport = productRepository.productMonthlyReport(LocalDate.now().getYear(), id);
        for (Object[] report : monthlyReport) {
            int month = (int) report[0];
            Long notifiedCount = (Long) report[1];
            Long soldCount = (Long) report[2];
            DataRequest monthReport = DataRequest.builder().name(Month.of(month).name()).peopleSold(soldCount)
                    .peopleNotified(notifiedCount).build();
            data.add(monthReport);
        }
        return data;
    }

    public ProductResponse addProduct(ProductRequest request, String token) {
        User manager = managerService.extractManager(token);
        Product product = Product.builder().name(request.getName()).price(request.getPrice())
                .producer(request.getProducer()).inStock(request.getInStock()).addedAt(LocalDate.now())
                .manager(manager)
                .build();
        productRepository.save(product);
        return new ProductResponse(product.getId(),
                product.getName(),
                product.getProducer(),
                product.getAddedAt(),
                product.getInStock(),
                product.getPrice(),
                Optional.ofNullable(product.getReports()).map(List::size).orElse(0),
                product.getManager());
    }

    public ProductChartResponse searchProduct(Product product, long id) {
        List<DataKeyRequest> dataKeys = List.of(DataKeyRequest.builder()
                .name("peopleNotified").build(), DataKeyRequest.builder()
                .name("peopleSold").build());
        List<DataRequest> data = chartInfo(id);
        ChartRequest info = ChartRequest.builder().data(data).dataKeys(dataKeys).build();
        return new ProductChartResponse(product, info);
    }

    public Long deleteProduct(Product product, Long id) {
        List<Report> productReports = product.getReports();
        for (Report report : productReports) {
            report.setProduct(null);
            reportRepository.save(report);
        }
        productRepository.delete(product);
        return id;
    }

    public ProductResponse editProduct(ProductUpdateRequest request, Long id) {
        Product productRequest = productRepository.findById(id).map(product -> {
            product.setInStock(Optional.ofNullable(request.getInStock()).orElse(product.getInStock()));
            product.setName(Optional.ofNullable(request.getName()).orElse(product.getName()));
            product.setPrice(Optional.ofNullable(request.getPrice()).orElse(product.getPrice()));
            product.setProducer(Optional.ofNullable(request.getProducer()).orElse(product.getProducer()));
            return productRepository.save(product);
        }).orElseThrow(ProductNotFoundException::new);
        return new ProductResponse(productRequest.getId(),
                productRequest.getName(),
                productRequest.getProducer(),
                productRequest.getAddedAt(),
                productRequest.getInStock(),
                productRequest.getPrice(),
                Optional.ofNullable(productRequest.getReports()).map(List::size).orElse(0),
                productRequest.getManager());
    }

    public List<ProductResponse> getManagerProducts(List<Product> products) {
        return products.stream().map(product -> new ProductResponse(product.getId(),
                product.getName(),
                product.getProducer(),
                product.getAddedAt(),
                product.getInStock(),
                product.getPrice(),
                Optional.ofNullable(product.getReports()).map(List::size).orElse(0),
                product.getManager())).collect(Collectors.toList());
    }
}