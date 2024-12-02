package com.sparta.msa_exam.order.controller;

import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import com.sparta.msa_exam.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto requestDto) {
        OrderResponseDto responseDto = orderService.createOrder(requestDto);

        return ResponseEntity.ok()
                .header("Server-Port", serverPort)
                .body(responseDto);
    }

    // 상품 API 호출 실패 케이스
    @PostMapping("/fail")
    public ResponseEntity<String> createOrderWithFail(@RequestParam Long productId) {
        String message = orderService.createOrderWithFail(productId);

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .header("Server-Port", serverPort)
                .body(message);
    }
}