package com.example.lihini_electrical.entity;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Orders {
    private String orderId;
    private String customerId;
    private Date date;

    private ArrayList<OrdersAndProductDetails> ordersAndProductDetailsDTOS;

    public Orders(String string, String string1, Date date) {
        this.orderId = string;
        this.customerId = string1;
        this.date = date;
    }
}
