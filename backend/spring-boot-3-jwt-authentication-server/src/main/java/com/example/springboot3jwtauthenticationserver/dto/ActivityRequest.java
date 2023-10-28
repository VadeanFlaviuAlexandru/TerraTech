package com.example.springboot3jwtauthenticationserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ActivityRequest {
    String description;
    Integer peopleNotifiedAboutProduct;
    Integer peopleSoldTo;
}
