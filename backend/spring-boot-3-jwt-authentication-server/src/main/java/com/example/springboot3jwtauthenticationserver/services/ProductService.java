package com.example.springboot3jwtauthenticationserver.services;

import com.example.springboot3jwtauthenticationserver.models.Product;
import com.example.springboot3jwtauthenticationserver.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product save(Product newProduct) {
        if (newProduct.getId() == null) {
            newProduct.setAddedAt(LocalDate.now());
        }
        return productRepository.save(newProduct);
    }
}
