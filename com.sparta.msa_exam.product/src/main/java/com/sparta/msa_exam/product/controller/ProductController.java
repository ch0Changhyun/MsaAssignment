package com.sparta.msa_exam.product.controller;

import com.sparta.msa_exam.product.dto.ProductRequestDto;
import com.sparta.msa_exam.product.dto.ProductResponseDto;
import com.sparta.msa_exam.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductRequestDto requestDto){
        ProductResponseDto responseDto = productService.addProduct(requestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Port", serverPort);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts();

        // 응답 헤더에 Server-Port 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Port", serverPort);

        return ResponseEntity.ok().headers(headers).body(products);
    }

}
