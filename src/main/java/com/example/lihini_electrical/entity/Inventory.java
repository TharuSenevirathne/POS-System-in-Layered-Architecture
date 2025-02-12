package com.example.lihini_electrical.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Inventory {
    private String inventoryId;
    private String type;
    private String stocklevel;
}
