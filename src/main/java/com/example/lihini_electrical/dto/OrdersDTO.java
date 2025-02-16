package com.example.lihini_electrical.dto;

import com.example.lihini_electrical.entity.Orders;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrdersDTO {
    private String orderId;
    private String customerId;
    private Date date;

    private ArrayList<OrdersAndProductDetailsDTO> ordersAndProductDetailsDTOS;

    public OrdersDTO(String string, String string1, Date date) {
        this.orderId = string;
        this.customerId = string1;
        this.date = date;
    }

    public Orders getOrderDetailsDTOS() {

        return null;
    }
}
