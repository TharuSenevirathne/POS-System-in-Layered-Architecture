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
        ArrayList<String> orderIds = ordersDAO.getAllOrderIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(orderIds);
        OrderidComboBox.setItems(observableList);
        return null;
    }

    @Override
    public OrdersDTO findById(String selectedOrderId) throws SQLException, ClassNotFoundException {
        String selectedOrderId = OrderidComboBox.getSelectionModel().getSelectedItem();
        OrdersDTO ordersDTO = ordersDAO.findById(selectedOrderId);

        if (ordersDTO != null) {
            System.out.println(" ");
        }
        return null;
    }

    @Override
    public String getNextOrderId() throws SQLException, ClassNotFoundException {
        return ordersDAO.getNextOrderId();
    }

    @Override
    public boolean saveOrder(OrdersDTO orderDTO) {
        return ordersDAO.save(new Orders(orderDTO.getOrderId(),orderDTO.getCustomerId(),orderDTO.getDate(),
                orderDTO.getOrdersAndProductDetailsDTOS());
    }

    @Override
    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDTO> supplierDTOArrayList = new ArrayList<>();
        ArrayList<Supplier> supplierDTOS = supplierDAO.getAllSuppliers();

        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();

        for (Supplier supplierDTO : supplierDTOS) {
            supplierDTOArrayList.add(supplierDTO.getSupplierId(),
                    supplierDTO.getName(),
                    supplierDTO.getBrand(),
                    supplierDTO.getPhoneNo());
        }
        return supplierDTOArrayList;
    }

    @Override
    public String getNextSupplierId() throws SQLException, ClassNotFoundException {
        String nextSupplierId = supplierDAO.getNextSupplierId();
        return nextSupplierId;
    }

    @Override
    public boolean deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(supplierId);
    }

    @Override
    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(supplierDTO.getSupplierId(),supplierDTO.getName(),supplierDTO.getBrand(),
                supplierDTO.getPhoneNo());
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(supplierDTO.getSupplierId(),supplierDTO.getName(),supplierDTO.getBrand(),
                supplierDTO.getPhoneNo());    }
}
