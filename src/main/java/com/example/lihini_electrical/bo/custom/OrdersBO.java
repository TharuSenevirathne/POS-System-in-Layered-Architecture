package com.example.lihini_electrical.bo.custom;

import com.example.lihini_electrical.bo.SuperBO;
import com.example.lihini_electrical.controller.OrdersController;
import com.example.lihini_electrical.dto.OrdersDTO;
import com.example.lihini_electrical.entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrdersBO extends SuperBO {
    ArrayList<String> getAllOrderIds() throws SQLException, ClassNotFoundException;
    String generateOrderId() throws SQLException, ClassNotFoundException;
    boolean saveOrder(OrdersDTO orderDTO) throws SQLException, ClassNotFoundException;

}
