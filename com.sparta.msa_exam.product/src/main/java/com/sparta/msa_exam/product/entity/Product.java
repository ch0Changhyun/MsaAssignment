package com.sparta.msa_exam.product.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "products")
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private  Integer supplyPrice;

    @Builder
    public Product(String name, Integer supplyPrice) {
        this.name = name;
        this.supplyPrice = supplyPrice;
    }

    public void update(String name, Integer supplyPrice) {
        this.name = name;
        this.supplyPrice = supplyPrice;
    }


}
