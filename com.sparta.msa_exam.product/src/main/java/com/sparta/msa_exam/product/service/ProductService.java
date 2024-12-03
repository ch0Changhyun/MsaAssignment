package com.sparta.msa_exam.product.service;

import com.sparta.msa_exam.product.dto.ProductRequestDto;
import com.sparta.msa_exam.product.dto.ProductResponseDto;
import com.sparta.msa_exam.product.entity.Product;
import com.sparta.msa_exam.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponseDto addProduct(ProductRequestDto requestDto){
        if(ProductRepository.existsByName(requestDto.getName())){
            throw new IllegalArgumentException("Product with the same name already exists!!");
        }

        Product product = Product.builder()
                .name(requestDto.getName())
                .supplyPrice(requestDto.getSupplyPrice())
                .build();

        productRepository.save(product);

        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .supplyPrice(product.getSupplyPrice())
                .build();
    }

    public List<ProductResponseDto> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductResponseDto(product.getProductId(), product.getName(), product.getSupplyPrice()))
                .collect(Collectors.toList());
    }

    public ProductResponseDto getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));
        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .supplyPrice(product.getSupplyPrice())
                .build();
    }
}
