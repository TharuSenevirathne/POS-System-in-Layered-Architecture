package com.example.lihini_electrical.bo.custom.impl;

import com.example.lihini_electrical.bo.custom.OrdersBO;
import com.example.lihini_electrical.controller.Orders;
import com.example.lihini_electrical.dao.DAOFactory;
import com.example.lihini_electrical.dao.custom.OrdersDAO;
import com.example.lihini_electrical.dao.custom.SupplierDAO;
import com.example.lihini_electrical.dto.OrdersDTO;
import com.example.lihini_electrical.dto.SupplierDTO;
import com.example.lihini_electrical.entity.Supplier;
import com.example.lihini_electrical.tdm.SupplierTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersBOImpl implements OrdersBO {
    OrdersDAO ordersDAO = (OrdersDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERS);
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    @Override
    public ArrayList<String> getAllOrderIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> orderIds = new ArrayList<>();
        ordersDAO.getAll();
        return orderIds;
    }

    public OrdersDTO searchOrder(String selectedOrderId) throws SQLException, ClassNotFoundException {
        Orders orders = ordersDAO.search(selectedOrderId);
        OrdersDTO ordersDTO = new OrdersDTO();
        return ordersDTO;
    }

    @Override
    public String generateOrderId() throws SQLException, ClassNotFoundException {
        return ordersDAO.generateId();
    }

    @Override
    public boolean saveOrder(OrdersDTO orderDTO) {
        return ordersDAO.save(new Orders(orderDTO.getOrderId(),orderDTO.getCustomerId(),orderDTO.getDate(),
                orderDTO.getOrdersAndProductDetailsDTOS()));
    }

    @Override
    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDTO> supplierDTOArrayList = new ArrayList<>();
        ArrayList<Supplier> supplierDTOS = supplierDAO.getAll();

        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();

        for (Supplier supplierDTO : supplierDTOS) {
            supplierDTOArrayList.add(new SupplierDTO((supplierDTO.getSupplierId()),supplierDTO.getName(),supplierDTO.getBrand(),
                    supplierDTO.getPhoneNo()));
        }
        return supplierDTOArrayList;
    }

    @Override
    public String generateSupplierId() throws SQLException, ClassNotFoundException {
        String nextSupplierId = supplierDAO.generateId();
        return nextSupplierId;
    }

    @Override
    public boolean deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(supplierId);
    }

    @Override
    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(new Supplier((supplierDTO.getSupplierId()),supplierDTO.getName(),supplierDTO.getBrand(),
                supplierDTO.getPhoneNo()));
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier((supplierDTO.getSupplierId()),supplierDTO.getName(),supplierDTO.getBrand(),
                supplierDTO.getPhoneNo()));
    }
}
