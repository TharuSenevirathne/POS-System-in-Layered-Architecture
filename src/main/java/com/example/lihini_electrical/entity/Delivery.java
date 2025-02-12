package com.example.lihini_electrical.entity;

import lombok.*;

import java.sql.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Delivery {
    private String deliveryId;
    private String address;
    private Date date;
    private String vehicleId;
}
