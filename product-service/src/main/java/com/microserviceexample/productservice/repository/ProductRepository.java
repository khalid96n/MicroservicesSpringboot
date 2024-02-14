package com.microserviceexample.productservice.repository;

import com.microserviceexample.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface ProductRepository extends MongoRepository<Product,String> {
}
