package com.example.springreactivewebflux.repository;

import com.example.springreactivewebflux.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
