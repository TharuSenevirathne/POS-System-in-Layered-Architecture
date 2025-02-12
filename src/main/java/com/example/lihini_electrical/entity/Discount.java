package com.example.lihini_electrical.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Discount {
    private String discountid;
    private double amount;
    private String orderid;

}
