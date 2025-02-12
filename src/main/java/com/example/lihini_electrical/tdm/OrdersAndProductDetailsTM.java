package com.example.lihini_electrical.tdm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrdersAndProductDetailsTM {
    private String orderId;
    private String productId;
    private Date date;
}
