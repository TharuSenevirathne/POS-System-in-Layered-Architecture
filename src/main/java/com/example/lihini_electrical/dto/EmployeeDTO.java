package com.example.lihini_electrical.dto;

import com.example.lihini_electrical.entity.Employee;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EmployeeDTO {
    private String employeeId;
    private String name;
    private String address;
    private String position;
    private String phoneNumber;

}
