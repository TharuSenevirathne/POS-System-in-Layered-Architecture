package com.example.lihini_electrical.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Warranty {
    private String warrantyId;
    private String productName;
    private String warrantyPeriodTime;
    private Date warrantyStartDate;
}
