package com.example.springreactivewebflux.service;

import com.example.springreactivewebflux.repository.ProductRepository;
import com.example.springreactivewebflux.dto.ProductDto;
import com.example.springreactivewebflux.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Flux<ProductDto> getAllProduct() {
        return this.productRepository.findAll().map(EntityDtoUtil::productDto);
    }

    public Mono<ProductDto> getProductById(String id) {
        return this.productRepository.findById(id).map(EntityDtoUtil::productDto);
    }

    public Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono) {
        return productDtoMono.map(EntityDtoUtil::product)
                .flatMap(this.productRepository::insert)
                .map(EntityDtoUtil::productDto);
    }

    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono) {
        return this.productRepository.findById(id)
                .flatMap(p -> productDtoMono.map(EntityDtoUtil::product)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(this.productRepository::save)
                .map(EntityDtoUtil::productDto);
    }

    public Mono<Void> deleteProduct(String id) {
        return this.productRepository.deleteById(id);
    }
}
