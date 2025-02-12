package com.example.lihini_electrical.tdm;

import lombok.*;

import java.sql.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PaymentTM {
    private String paymentID;
    private double amount;
    private String orderItemName;
    private Date date;
}

