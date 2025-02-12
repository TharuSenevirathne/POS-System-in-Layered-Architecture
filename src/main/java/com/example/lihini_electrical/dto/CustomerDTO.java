package com.example.lihini_electrical.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerDTO {
    private String customerId;
    private String name;
    private String address;
    private String phoneNo;
    private String email;
    private String type;
    private String employeeId;
}