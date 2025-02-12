package com.example.lihini_electrical.bo.custom;

import com.example.lihini_electrical.dto.OrdersDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrdersBO extends SupplierBO {
    ArrayList<String> getAllOrderIds() throws SQLException, ClassNotFoundException;
    OrdersDTO findById(String selectedOrderId) throws SQLException, ClassNotFoundException;
    String getNextOrderId() throws SQLException, ClassNotFoundException;
    boolean saveOrder(OrdersDTO orderDTO);
}
