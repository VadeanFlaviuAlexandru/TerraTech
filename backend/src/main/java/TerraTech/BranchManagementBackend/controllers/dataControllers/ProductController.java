package TerraTech.BranchManagementBackend.controllers.dataControllers;

import TerraTech.BranchManagementBackend.dto.data.product.ProductRequest;
import TerraTech.BranchManagementBackend.dto.data.product.ProductUpdateRequest;
import TerraTech.BranchManagementBackend.dto.data.product.ProductChartResponse;
import TerraTech.BranchManagementBackend.dto.data.product.ProductResponse;
import TerraTech.BranchManagementBackend.services.dataServices.ProductService;
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

    @PostMapping("/product/addProduct")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public ProductResponse addProduct(@RequestBody ProductRequest request, @RequestHeader("Authorization") String token) {
        return productService.addProduct(request, token);
    }

    @GetMapping("/product/searchProduct/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public ProductChartResponse searchProduct(@PathVariable Long id) {
        return productService.searchProduct(id);
    }

    @DeleteMapping("/product/deleteProduct/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @PutMapping("/product/editProduct/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public ProductResponse editProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long id) {
        return productService.editProduct(request, id);
    }

    @GetMapping("/product/getManagerProducts/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public List<ProductResponse> getManagerProducts(@PathVariable Long id) {
        return productService.getManagerProducts(id);
    }


}
