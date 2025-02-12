package com.example.lihini_electrical.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SupplierAndProductDetails {
    private String supplierId;
    private String productId;
    private Date date;
}
