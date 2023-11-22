package TerraTech.BranchManagementBackend.services.dataServices;

import TerraTech.BranchManagementBackend.dto.chart.user.ChartRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.DataKeyRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.DataRequest;
import TerraTech.BranchManagementBackend.dto.data.product.ProductRequest;
import TerraTech.BranchManagementBackend.dto.data.product.ProductUpdateRequest;
import TerraTech.BranchManagementBackend.dto.data.product.ProductChartResponse;
import TerraTech.BranchManagementBackend.dto.data.product.ProductResponse;
import TerraTech.BranchManagementBackend.exceptions.data.product.ProductNotFoundException;
import TerraTech.BranchManagementBackend.exceptions.data.product.ProductSameNameException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ReportRepository reportRepository;

    public ProductResponse addProduct(ProductRequest request, String token) {
        var tokenSubstring = ExtractToken.extractToken(token);
        var manager = userRepository.findByEmail(jwtService.extractUserName(tokenSubstring)).orElseThrow(ManagerNotFoundException::new);
        productRepository.findByName(request.getName()).orElseThrow(ProductSameNameException::new);
        var product = Product.builder().name(request.getName()).price(request.getPrice()).producer(request.getProducer()).inStock(request.getInStock()).addedAt(LocalDate.now()).manager(manager).build();
        productRepository.save(product);
        return ProductResponse.builder().name(product.getName()).price(product.getPrice()).producer(product.getProducer()).inStock(product.getInStock()).addedAt(product.getAddedAt()).manager(product.getManager()).build();
    }

    public ProductChartResponse searchProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        List<DataRequest> dataList = new ArrayList<>();
        List<DataKeyRequest> dataKeyRequestList = List.of(DataKeyRequest.builder().name("peopleNotifiedAboutProduct").build(), DataKeyRequest.builder().name("peopleSoldTo").build());

        for (int month = 1; month <= 12; month++) {
            LocalDate currentMonth = LocalDate.of(LocalDate.now().getYear(), month, 1);
            Long totalNotifiedPeopleSpecificMonth = productRepository.productPeopleSoldToSpecificMonth(currentMonth.getMonthValue(), currentMonth.getYear(), id);
            Long totalSoldPeopleSpecificMonth = productRepository.productNotifiedPeopleSpecificMonth(currentMonth.getMonthValue(), currentMonth.getYear(), id);
            DataRequest monthReport = DataRequest.builder().name(currentMonth.getMonth().name()).peopleSoldTo(totalSoldPeopleSpecificMonth).peopleNotifiedAboutProduct(totalNotifiedPeopleSpecificMonth).build();
            dataList.add(monthReport);
        }

        ChartRequest info = ChartRequest.builder().data(dataList).dataKeys(dataKeyRequestList).build();

        return ProductChartResponse.builder().product(product).chartInfo(info).build();
    }

    public ResponseEntity<String> deleteProduct(Long id) {
        try {
            Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
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

    public ProductResponse editProduct(ProductUpdateRequest request, Long id) {
        Product productRequest = productRepository.findById(id).map(product -> {
            request.getInStock().ifPresent(product::setInStock);
            request.getName().ifPresent(product::setName);
            request.getPrice().ifPresent(product::setPrice);
            request.getProducer().ifPresent(product::setProducer);
            return productRepository.save(product);
        }).orElseThrow(ProductNotFoundException::new);
        return ProductResponse.builder().id(productRequest.getId()).inStock(productRequest.getInStock()).name(productRequest.getName()).price(productRequest.getPrice()).producer(productRequest.getProducer()).addedAt(productRequest.getAddedAt()).build();
    }

    public List<ProductResponse> getManagerProducts(Long id) {
        List<Product> products = productRepository.findByManagerId(id);
        return products.stream().map(product -> ProductResponse.builder().name(product.getName()).price(product.getPrice()).producer(product.getProducer()).inStock(product.getInStock()).addedAt(product.getAddedAt()).manager(product.getManager()).build()).collect(Collectors.toList());
    }
}