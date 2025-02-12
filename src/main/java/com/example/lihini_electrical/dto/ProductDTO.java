package com.example.lihini_electrical.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProductDTO {
    private String productId;
    private String name;
    private double price;
    private String inventoryId;
    private String quantity;
}
