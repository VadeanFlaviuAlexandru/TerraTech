package com.example.springboot3jwtauthenticationserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    Integer price;
    String producer;
    List<String> Colours;
    Integer inStock;
    String name;
}
