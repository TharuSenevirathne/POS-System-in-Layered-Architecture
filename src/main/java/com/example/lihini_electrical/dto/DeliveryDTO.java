package com.example.lihini_electrical.dto;

import lombok.*;

import java.sql.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class DeliveryDTO {
    private String deliveryId;
    private String address;
    private Date date;
    private String vehicleId;
}
