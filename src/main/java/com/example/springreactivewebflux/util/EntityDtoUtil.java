package com.example.springreactivewebflux.util;

import com.example.springreactivewebflux.dto.ProductDto;
import com.example.springreactivewebflux.entity.Product;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {
    public static ProductDto productDto(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    public static Product product(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}
