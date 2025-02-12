package com.example.lihini_electrical.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Customer {
    private String customerId;
    private String name;
    private String address;
    private String phoneNo;
    private String email;
    private String type;
    private String employeeId;

}