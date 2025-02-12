package com.example.lihini_electrical.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class DiscountDTO {
    private String discountid;
    private double amount;
    private String orderid;

}
