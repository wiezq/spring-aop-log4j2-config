package com.example.t1_task3.service;

import com.example.t1_task3.model.ProductEntity;
import com.example.t1_task3.repository.ProductEntityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private final ProductEntityRepository productRepository;


    public ProductEntity createProduct(String name, double price) {
        ProductEntity product = new ProductEntity();
        product.setName(name);
        product.setPrice(price);
        return productRepository.save(product);
    }


    public ProductEntity getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));
    }

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductEntity updateProduct(Long id, String name, double price) {
        ProductEntity product = getProductById(id);
        product.setName(name);
        product.setPrice(price);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        ProductEntity product = getProductById(id);
        productRepository.delete(product);
    }

    public List<ProductEntity> getProductsByIds(List<Long> productIds) {
        return productRepository.findAllById(productIds);
    }


}
