package com.example.lihini_electrical.dao.custom;

import com.example.lihini_electrical.controller.Orders;
import com.example.lihini_electrical.dao.CrudDAO;
import com.example.lihini_electrical.dto.OrdersDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrdersDAO extends CrudDAO<Orders> {
    ArrayList<String> getAllOrderIds() throws SQLException, ClassNotFoundException;
    OrdersDTO findById(String selectedOrderId) throws SQLException, ClassNotFoundException;
    String getNextOrderId() throws SQLException, ClassNotFoundException;
    boolean save(Orders orders);
}
