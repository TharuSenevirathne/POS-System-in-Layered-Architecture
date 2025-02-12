package com.example.lihini_electrical.tdm;

import javafx.scene.control.Button;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartTM {
    private String productId;
    private String productname;
    private double cartQuantity;
    private double unitPrice;
    private double total;
    private String customerId;
    private Button remove;
}
