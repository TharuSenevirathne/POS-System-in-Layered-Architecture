package com.example.lihini_electrical.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrdersAndProductDetails {
    private String orderId;
    private String productId;
    private double cartQuantity;
    private double unitprice;

    public OrdersAndProductDetails(String productId, String productname, double cartQuantity, double unitPrice, double total, String customerId) {
    this.productId = productId;
    }
}