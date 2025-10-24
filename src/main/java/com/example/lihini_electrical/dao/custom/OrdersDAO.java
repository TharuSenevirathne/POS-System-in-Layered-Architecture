package com.example.lihini_electrical.dao.custom;

import com.example.lihini_electrical.dao.CrudDAO;
import com.example.lihini_electrical.dto.OrdersAndProductDetailsDTO;
import com.example.lihini_electrical.entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrdersDAO extends CrudDAO<Orders> {
    Orders search(String orderId) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException;
    String generateId() throws SQLException, ClassNotFoundException;
    boolean save(Orders order) throws SQLException, ClassNotFoundException;
    boolean saveOrderDetails(ArrayList<OrdersAndProductDetailsDTO> details) throws SQLException, ClassNotFoundException;
    boolean update(Orders order) throws SQLException, ClassNotFoundException;
    ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException;
    boolean delete(String orderId) throws SQLException, ClassNotFoundException;
    ArrayList<OrdersAndProductDetailsDTO> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException;
}
