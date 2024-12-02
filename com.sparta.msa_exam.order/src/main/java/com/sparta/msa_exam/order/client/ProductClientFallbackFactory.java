package com.sparta.msa_exam.order.client;

import com.sparta.msa_exam.product.dto.ProductResponseDto;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ProductClientFallbackFactory implements FallbackFactory<ProductClient> {

    @Override
    public ProductClient create(Throwable cause) {
        return new ProductClient() {
            @Override
            public ProductResponseDto getProductById(Long productId) {
                // Fallback 응답 메시지 생성
                throw new IllegalStateException("잠시 후에 주문 추가를 요청 해주세요.");
            }
        };
    }
}
