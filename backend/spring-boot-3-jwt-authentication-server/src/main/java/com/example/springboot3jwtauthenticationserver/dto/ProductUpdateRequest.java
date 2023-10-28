package com.example.springboot3jwtauthenticationserver.dto;

import com.example.springboot3jwtauthenticationserver.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
public class ProductUpdateRequest {
    private Optional<Integer> price;
    private Optional<String> producer;
    private Optional<List<String>> colours;
    private Optional<Integer> inStock;
    private Optional<String> name;

    public ProductUpdateRequest() {
        this.price = Optional.empty();
        this.producer = Optional.empty();
        this.colours = Optional.empty();
        this.inStock = Optional.empty();
        this.name = Optional.empty();
    }
}