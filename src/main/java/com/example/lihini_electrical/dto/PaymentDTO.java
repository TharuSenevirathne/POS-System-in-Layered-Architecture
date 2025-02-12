package com.example.lihini_electrical.dto;

import com.example.lihini_electrical.entity.Payment;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PaymentDTO {
    private String paymentID;
    private double amount;
    private String orderItemName;
    private Date date;

}
