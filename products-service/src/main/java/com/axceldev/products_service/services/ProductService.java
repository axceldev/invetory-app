package com.axceldev.products_service.services;

import com.axceldev.products_service.model.dtos.rq.ProductRequest;
import com.axceldev.products_service.model.dtos.rs.ProductResponse;
import com.axceldev.products_service.model.entities.Product;
import com.axceldev.products_service.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void addProduct(ProductRequest productRequest) {

        Product product = Product.builder()
                .sku(productRequest.getSku())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .status(productRequest.isStatus())
                .build();

        productRepository.save(product);

        log.info("Product added: {} ", product);

    }

    public List<ProductResponse> getAllProducts(){
        var products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .idProduct(product.getIdProduct())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .status(product.isStatus())
                .build();
    }
}
