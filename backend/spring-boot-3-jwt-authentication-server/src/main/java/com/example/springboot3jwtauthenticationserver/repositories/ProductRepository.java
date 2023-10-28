package com.example.springboot3jwtauthenticationserver.repositories;

import com.example.springboot3jwtauthenticationserver.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByManagerId(Long managerId);

}
