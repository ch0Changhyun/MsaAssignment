package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.client.ProductClient;
import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import com.sparta.msa_exam.order.dto.ProductResponseDto;
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

    @Transactional public String createOrderWithFail(Long productId){
        try{
            ProductResponseDto product = productClient.getProductById(productId);
            if (product.getMessage() != null) {
                return product.getMessage();
            }
        } catch (Exception e) {
            return "잠시 후에 주문 추가를 요청 해주세요.";
        }
        return "주문이 정상 처리 되었습니다.";
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long orderId) {
        // 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        // 응답 DTO 생성
        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .productIds(order.getOrderProducts()
                        .stream()
                        .map(OrderProduct::getProductId)
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public OrderResponseDto addProductToOrder(Long orderId, OrderRequestDto requestDto) {
        // 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        // 요청된 상품 ID 리스트를 순회하면서 처리
        for (Long productId : requestDto.getProductIds()) {
            // 상품 존재 여부 확인 (상품이 존재하지 않으면 예외 처리)
            try {
                productClient.getProductById(productId);  // 외부 상품 API 호출
            } catch (Exception e) {
                // 상품이 존재하지 않거나 호출 실패 시 예외 처리
                throw new IllegalArgumentException("Product not found with ID: " + productId);
            }

            // 주문에 상품 추가
            OrderProduct orderProduct = new OrderProduct(productId);
            order.addOrderProduct(orderProduct);
        }

        // 주문 저장
        Order updatedOrder = orderRepository.save(order);

        // 응답 생성
        return OrderResponseDto.builder()
                .orderId(updatedOrder.getOrderId())
                .productIds(updatedOrder.getOrderProducts()
                        .stream()
                        .map(OrderProduct::getProductId)
                        .collect(Collectors.toList()))
                .build();
    }
}
