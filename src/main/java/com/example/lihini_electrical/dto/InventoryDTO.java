package com.example.lihini_electrical.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class InventoryDTO {
    private String inventoryId;
    private String type;
    private String stocklevel;
}
