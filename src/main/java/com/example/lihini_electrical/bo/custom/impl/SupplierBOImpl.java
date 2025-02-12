package com.example.lihini_electrical.bo.custom.impl;

import com.example.lihini_electrical.bo.custom.SupplierBO;
import com.example.lihini_electrical.dao.DAOFactory;
import com.example.lihini_electrical.dao.custom.impl.custom.SupplierDAO;
import com.example.lihini_electrical.dto.SupplierDTO;
import com.example.lihini_electrical.entity.Supplier;
import com.example.lihini_electrical.tdm.SupplierTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    @Override
    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDTO> suppliers = new ArrayList<>();
        ArrayList<Supplier> suppliers1 = supplierDAO.getAll();
        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();

        for (Supplier supplier : suppliers1) {
            suppliers.add(new SupplierDTO(supplier.getSupplierId(),
                    supplier.getName(),
                    supplier.getBrand(),
                    supplier.getPhoneNo()));
        }
        return suppliers;
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
        return supplierDAO.save(new Supplier(supplierDTO.getSupplierId(),supplierDTO.getName(),supplierDTO.getBrand(),
                supplierDTO.getPhoneNo()));
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(supplierDTO.getSupplierId(),supplierDTO.getName(),supplierDTO.getBrand(),
                supplierDTO.getPhoneNo()));    }
}
