package com.example.lihini_electrical.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SupplierAndProductDetailsDTO {
    private String supplierId;
    private String productId;
    private Date date;
}
