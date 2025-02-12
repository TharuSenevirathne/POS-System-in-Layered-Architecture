package com.example.lihini_electrical.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrdersAndProductDetailsDTO {
    private String orderId;
    private String productId;
    private double cartQuantity;
    private double unitprice;

    public OrdersAndProductDetailsDTO(String productId, String productname, double cartQuantity, double unitPrice, double total, String customerId) {
    this.productId = productId;
    }
}