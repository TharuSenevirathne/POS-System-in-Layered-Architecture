package com.example.lihini_electrical.tdm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrdersTM {
    private String orderId;
    private double totalPrice;
    private Date date;
    private String status;
    private String customerId;
    private String paymentId;
    private String deliveryId;
}
