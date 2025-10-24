package com.example.lihini_electrical.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Product {
    private String productId;
    private String name;
    private double price;
    private String quantity;
    private String inventoryId;
}
