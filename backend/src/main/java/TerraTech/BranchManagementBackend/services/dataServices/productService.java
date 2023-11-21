package TerraTech.BranchManagementBackend.services.dataServices;

import TerraTech.BranchManagementBackend.dto.chart.user.chartRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.dataKeyRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.dataRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.reportRequest;
import TerraTech.BranchManagementBackend.dto.data.product.productRequest;
import TerraTech.BranchManagementBackend.dto.data.product.productUpdateRequest;
import TerraTech.BranchManagementBackend.dto.data.product.productResponse;
import TerraTech.BranchManagementBackend.exceptions.data.product.productNotFoundException;
import TerraTech.BranchManagementBackend.exceptions.data.product.productSameNameException;
import TerraTech.BranchManagementBackend.exceptions.manager.managerNotFoundException;
import TerraTech.BranchManagementBackend.models.Product;
import TerraTech.BranchManagementBackend.models.Report;
import TerraTech.BranchManagementBackend.models.User;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class productService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ReportRepository reportRepository;

    public Product addProduct(productRequest request, String token) {
        var tokenSubstring = token.substring(7);
        var productSearch = productRepository.findByName(request.getName());

        if (productSearch.isPresent()) {
            throw new productSameNameException();
        }

        var product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .producer(request.getProducer())
                .inStock(request.getInStock())
                .addedAt(LocalDate.now())
                .manager(userRepository.findByEmail(jwtService.extractUserName(tokenSubstring)).
                        orElseThrow(managerNotFoundException::new))
                .build();
        product = productRepository.save(product);
        return product;
    }

    public productResponse searchProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(productNotFoundException::new);
        List<dataRequest> dataList = new ArrayList<>();
        List<dataKeyRequest> dataKeyRequestList = List.of(dataKeyRequest.builder().name("peopleNotifiedAboutProduct").build(), dataKeyRequest.builder().name("peopleSoldTo").build());

        for (int month = 1; month <= 12; month++) {
            LocalDate currentMonth = LocalDate.of(LocalDate.now().getYear(), month, 1);
            Long totalNotifiedPeopleSpecificMonth = productRepository.productPeopleSoldToSpecificMonth(currentMonth.getMonthValue(), currentMonth.getYear(), id);
            Long totalSoldPeopleSpecificMonth = productRepository.productNotifiedPeopleSpecificMonth(currentMonth.getMonthValue(), currentMonth.getYear(), id);
            dataRequest monthReport = dataRequest.builder().name(currentMonth.getMonth().name()).peopleSoldTo(totalSoldPeopleSpecificMonth).peopleNotifiedAboutProduct(totalNotifiedPeopleSpecificMonth).build();
            dataList.add(monthReport);
        }
        chartRequest info = chartRequest.builder().data(dataList).dataKeys(dataKeyRequestList).build();
        return productResponse.builder().product(product).chartInfo(info).build();
    }

    public ResponseEntity<String> deleteProduct(Long id) {
        try {
            Product product = productRepository.findById(id).orElseThrow(productNotFoundException::new);
            if (product != null) {
                List<Report> productReports = product.getReports();
                for (Report report : productReports) {
                    report.setProduct(null);
                    reportRepository.save(report);
                }
                productRepository.delete(product);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product deleted successfully!");
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    public Product editProduct(productUpdateRequest request, Long id) {
        return productRepository.findById(id).map(product -> {
            request.getInStock().ifPresent(product::setInStock);
            request.getName().ifPresent(product::setName);
            request.getPrice().ifPresent(product::setPrice);
            request.getProducer().ifPresent(product::setProducer);
            return productRepository.save(product);
        }).orElseThrow(productNotFoundException::new);
    }

    public List<Product> getManagerProducts(Long id) {
        return productRepository.findByManagerId(id);
    }
}
