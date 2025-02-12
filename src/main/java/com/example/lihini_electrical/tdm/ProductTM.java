package com.example.lihini_electrical.tdm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class ProductTM {
    private String productId;
    private String name;
    private double price;
    private String inventoryId;
    private String quantity;
}
