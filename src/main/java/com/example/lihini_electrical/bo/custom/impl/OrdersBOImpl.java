package com.example.lihini_electrical.bo.custom.impl;

import com.example.lihini_electrical.bo.custom.OrdersBO;
import com.example.lihini_electrical.controller.Orders;
import com.example.lihini_electrical.dao.DAOFactory;
import com.example.lihini_electrical.dao.custom.OrdersDAO;
import com.example.lihini_electrical.dao.custom.SupplierDAO;
import com.example.lihini_electrical.dto.OrdersAndProductDetailsDTO;
import com.example.lihini_electrical.entity.OrdersAndProductDetails;
import com.example.lihini_electrical.dto.OrdersDTO;



import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersBOImpl implements OrdersBO {
    OrdersDAO ordersDAO = (OrdersDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERS);

    @Override
    public ArrayList<String> getAllOrderIds() throws SQLException, ClassNotFoundException {
        return ordersDAO.getAllIds();
    }

    public Orders searchOrder(String selectedOrderId) throws SQLException, ClassNotFoundException {
        return ordersDAO.search(selectedOrderId);
    }

    @Override
    public String generateOrderId() throws SQLException, ClassNotFoundException {
        return ordersDAO.generateId();
    }

    @Override
    public boolean saveOrder(OrdersDTO orderDTO) throws SQLException, ClassNotFoundException {
        ArrayList<OrdersAndProductDetails> ordersAndProductDetails = new ArrayList<>();

        for (OrdersAndProductDetailsDTO dto :  ordersAndProductDetails1) {
            OrdersAndProductDetails orderDetails = new OrdersAndProductDetails(
                    dto.getOrderId(),
                    dto.getProductId(),
                    dto.getCartQuantity(),
                    dto.getUnitprice()
            );
            ordersAndProductDetails.add(orderDetails);
        }


        return ordersDAO.save(orderDTO);
    }

}
