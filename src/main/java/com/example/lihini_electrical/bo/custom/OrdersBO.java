package com.example.lihini_electrical.bo.custom;

import com.example.lihini_electrical.controller.Orders;
import com.example.lihini_electrical.dto.OrdersDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrdersBO extends SupplierBO {
    ArrayList<String> getAllOrderIds() throws SQLException, ClassNotFoundException;
    Orders searchOrder(String selectedOrderId) throws SQLException, ClassNotFoundException;
    String generateOrderId() throws SQLException, ClassNotFoundException;
    boolean saveOrder(OrdersDTO orderDTO) throws SQLException, ClassNotFoundException;

}
