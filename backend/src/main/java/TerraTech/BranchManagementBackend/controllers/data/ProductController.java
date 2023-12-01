package TerraTech.BranchManagementBackend.controllers.data;

import TerraTech.BranchManagementBackend.dto.data.product.ProductRequest;
import TerraTech.BranchManagementBackend.dto.data.product.ProductUpdateRequest;
import TerraTech.BranchManagementBackend.dto.data.product.ProductChartResponse;
import TerraTech.BranchManagementBackend.dto.data.product.ProductResponse;
import TerraTech.BranchManagementBackend.exceptions.data.product.ProductNotFoundException;
import TerraTech.BranchManagementBackend.exceptions.data.product.ProductSameNameException;
import TerraTech.BranchManagementBackend.models.Product;
import TerraTech.BranchManagementBackend.repositories.ProductRepository;
import TerraTech.BranchManagementBackend.services.data.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/TerraTechInc")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @PostMapping("/product/addProduct")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public ProductResponse addProduct(@RequestBody ProductRequest request, @RequestHeader("Authorization") String token) {
        productRepository.findByName(request.getName()).ifPresent(user -> {
            throw new ProductSameNameException(request.getName());
        });
        return productService.addProduct(request, token);
    }

    @GetMapping("/product/searchProduct/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public ProductChartResponse searchProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        return productService.searchProduct(product, id);
    }

    @DeleteMapping("/product/deleteProduct/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        Long deletedId = productService.deleteProduct(product, id);
        return ResponseEntity.ok("Product with id: " + deletedId + "deleted successfully");
    }

    @PutMapping("/product/editProduct/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public ProductResponse editProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long id) {
        return productService.editProduct(request, id);
    }

    @GetMapping("/product/getManagerProducts/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN') || hasRole('ROLE_EMPLOYEE')")
    public List<ProductResponse> getManagerProducts(@PathVariable Long id) {
        List<Product> products = productRepository.findByManagerId(id);
        return productService.getManagerProducts(products);
    }


}
