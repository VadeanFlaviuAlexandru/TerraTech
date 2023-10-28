package com.example.springboot3jwtauthenticationserver.controllers;

import com.example.springboot3jwtauthenticationserver.dto.ProductRequest;
import com.example.springboot3jwtauthenticationserver.dto.ProductUpdateRequest;
import com.example.springboot3jwtauthenticationserver.exception.UserNotFoundException;
import com.example.springboot3jwtauthenticationserver.models.Product;
import com.example.springboot3jwtauthenticationserver.repositories.ManagerRepository;
import com.example.springboot3jwtauthenticationserver.repositories.ProductRepository;
import com.example.springboot3jwtauthenticationserver.repositories.UserRepository;
import com.example.springboot3jwtauthenticationserver.services.JwtService;
import com.example.springboot3jwtauthenticationserver.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/TerraTechInc")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final JwtService jwtService;
    private final ManagerRepository managerRepository;

    @PostMapping("/managers/addProduct")
    @PreAuthorize("hasRole('MANAGER')")
    public Product addProduct(String token, @RequestBody ProductRequest request) {
        var product = Product.builder()
                .price(request.getPrice())
                .producer(request.getProducer())
                .inStock(request.getInStock())
                .addedAt(LocalDate.now())
                .colours(request.getColours())
                .name(request.getName())
                .manager(jwtService.extractManager(token))
                .build();
        productService.save(product);
        return product;
    }

    @PutMapping("/managers/editProduct/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public Product updateProductProfile(@PathVariable Long id, @RequestBody ProductUpdateRequest newUser) {
        return productRepository.findById(id).map(product -> {
            newUser.getPrice().ifPresent(price -> product.setPrice(price));
            newUser.getProducer().ifPresent(producer -> product.setProducer(producer));
            newUser.getColours().ifPresent(colours -> product.setColours(colours));
            newUser.getInStock().ifPresent(inStock -> product.setInStock(inStock));
            newUser.getName().ifPresent(name -> product.setName(name));
            return productRepository.save(product);
        }).orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping("/managers/products/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public Product getProductProfile(@PathVariable Long id) {
        return productRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @DeleteMapping("/managers/products/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> deleteProductProfile(@PathVariable Long id) {
        try {
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product deleted successfully");
        } catch (EmptyResultDataAccessException ex) {
            // If the product with the given ID does not exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        } catch (Exception ex) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @GetMapping("/managers/products")
    @PreAuthorize("hasRole('MANAGER')")
    public List<Product> getProducts(String token, @PathVariable Long id) {
        return productRepository.findByManagerId(jwtService.extractId(token));
    }

}
