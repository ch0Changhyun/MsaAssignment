package com.sparta.msa_exam.order.client;

import com.sparta.msa_exam.order.dto.ProductResponseDto;
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
                ProductResponseDto fallbackResponse = new ProductResponseDto();
                fallbackResponse.setProductId(productId);
                fallbackResponse.setName("Unavailable");
                fallbackResponse.setSupplyPrice(0);
                fallbackResponse.setMessage("잠시 후에 주문 추가를 요청 해주세요.");
                return fallbackResponse;
            }
        };
    }
}
