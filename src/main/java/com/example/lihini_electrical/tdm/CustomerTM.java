package com.example.lihini_electrical.tdm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerTM {
    private String customerId;
    private String name;
    private String address;
    private String phoneNo;
    private String email;
    private String type;
    private String employeeId;
}
