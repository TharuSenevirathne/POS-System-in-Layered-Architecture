package com.example.lihini_electrical.tdm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class WarrantyTM {
    private String warrantyId;
    private String productName;
    private String warrantyPeriodTime;
    private Date warrantyStartDate;

}
