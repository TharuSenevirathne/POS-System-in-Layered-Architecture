package com.example.lihini_electrical.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class WarrantyDTO {
    private String warrantyId;
    private String productName;
    private String warrantyPeriodTime;
    private Date warrantyStartDate;
}
