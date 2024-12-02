package com.sparta.msa_exam.order.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderResponseDto {
    private Long orderId;
    private List<Long> productIds;
}