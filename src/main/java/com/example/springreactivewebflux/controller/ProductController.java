package com.example.springreactivewebflux.controller;

import com.example.springreactivewebflux.dto.ProductDto;
import com.example.springreactivewebflux.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/allProduct")
    public Flux<ProductDto> getAllProduct() {
        return this.productService.getAllProduct();
    }

    @GetMapping("/product/{id}")
    public Mono<ResponseEntity<ProductDto>> getProductById(@PathVariable String id) {
        return this.productService.getProductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ProductDto> insertProduct(@RequestBody Mono<ProductDto> productDtoMono) {
        return this.productService.insertProduct(productDtoMono);
    }

    @PutMapping("/product/{id}")
    public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable String id, @RequestBody Mono<ProductDto> productDtoMono) {
        return this.productService.updateProduct(id, productDtoMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/product/{id}")
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return this.productService.deleteProduct(id);
    }
}
