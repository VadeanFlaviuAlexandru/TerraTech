package TerraTech.BranchManagementBackend.controllers.dataControllers;

import TerraTech.BranchManagementBackend.dto.data.product.productRequest;
import TerraTech.BranchManagementBackend.dto.data.product.productUpdateRequest;
import TerraTech.BranchManagementBackend.dto.data.product.productResponse;
import TerraTech.BranchManagementBackend.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/TerraTechInc")
@RequiredArgsConstructor
public class productController {

    private final TerraTech.BranchManagementBackend.services.dataServices.productService productService;

    @PostMapping("/product/addProduct")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public Product addProduct(@RequestBody productRequest request, @RequestHeader("Authorization") String token) {
        return productService.addProduct(request, token);
    }

    @GetMapping("/product/searchProduct/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public productResponse searchProduct(@PathVariable Long id) {
        return productService.searchProduct(id);
    }

    @DeleteMapping("/product/deleteProduct/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @PutMapping("/product/editProduct/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public Product editProduct(@RequestBody productUpdateRequest request, @PathVariable Long id) {
        return productService.editProduct(request, id);
    }

    @GetMapping("/product/getManagerProducts/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public List<Product> getManagerProducts(@PathVariable Long id) {
        return productService.getManagerProducts(id);
    }


}
