package com.example.lihini_electrical.bo.custom.impl;

import com.example.lihini_electrical.bo.custom.OrdersBO;
import com.example.lihini_electrical.dao.DAOFactory;
import com.example.lihini_electrical.dao.custom.OrdersDAO;
import com.example.lihini_electrical.db.DBConnection;
import com.example.lihini_electrical.dto.OrdersDTO;
import com.example.lihini_electrical.entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersBOImpl implements OrdersBO {

    OrdersDAO ordersDAO = (OrdersDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERS);

    @Override
    public ArrayList<String> getAllOrderIds() throws SQLException, ClassNotFoundException {
        return ordersDAO.getAllIds();
    }

    @Override
    public String generateOrderId() throws SQLException, ClassNotFoundException {
        return ordersDAO.generateId();
    }

    public boolean saveOrder(OrdersDTO orderDTO) throws SQLException, ClassNotFoundException {
        boolean orderSaved = ordersDAO.save(new Orders(
                orderDTO.getOrderId(),
                orderDTO.getCustomerId(),
                orderDTO.getDate()
        ));

        if (!orderSaved) return false;

        return ordersDAO.saveOrderDetails(orderDTO.getOrderDetailsDTOS());
    }

}
