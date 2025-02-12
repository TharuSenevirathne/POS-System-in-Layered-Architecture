package com.example.lihini_electrical.tdm;

import lombok.*;

import java.sql.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class DeliveryTM {
    private String deliveryId;
    private String address;
    private Date date;
    private String vehicleId;
}
