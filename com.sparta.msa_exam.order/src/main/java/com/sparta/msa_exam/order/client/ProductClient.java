package com.sparta.msa_exam.order.client;

import com.sparta.msa_exam.product.dto.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", fallbackFactory = ProductClientFallbackFactory.class)
public interface ProductClient {

    @GetMapping("/products/{productId}")
    ProductResponseDto getProductById(@PathVariable Long productId);
}
