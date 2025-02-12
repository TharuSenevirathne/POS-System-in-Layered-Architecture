package com.example.lihini_electrical.tdm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SupplierAndProductDetailsTM {
    private String supplierId;
    private String productId;
    private Date date;
}
