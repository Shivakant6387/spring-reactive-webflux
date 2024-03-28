package com.example.springreactivewebflux.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDto {
    private String id;
    private String description;
    private String price;
}
