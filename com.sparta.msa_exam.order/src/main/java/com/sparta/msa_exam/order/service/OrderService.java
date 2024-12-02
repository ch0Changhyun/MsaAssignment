package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.client.ProductClient;
import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;
import com.sparta.msa_exam.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        Order order = new Order();

        // 주문 상품 매핑
        for (Long productId : requestDto.getProductIds()) {
            OrderProduct orderProduct = new OrderProduct(productId);
            order.addOrderProduct(orderProduct);
        }

        // 주문 저장
        Order savedOrder = orderRepository.save(order);

        // 응답 DTO 생성
        return OrderResponseDto.builder()
                .orderId(savedOrder.getOrderId())
                .productIds(savedOrder.getOrderProducts()
                        .stream()
                        .map(OrderProduct::getProductId)
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public String createOrderWithFail(Long productId){
        try{
            productClient.getProductById(productId);
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
        return "주문이 정상 처리 되었습니다.";
    }
}
