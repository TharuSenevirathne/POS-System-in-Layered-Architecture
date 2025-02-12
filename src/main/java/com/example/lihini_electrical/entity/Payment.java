package com.example.lihini_electrical.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Payment {
    private String paymentID;
    private double amount;
    private String orderItemName;
    private Date date;

}
