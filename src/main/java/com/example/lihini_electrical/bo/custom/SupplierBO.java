package com.example.lihini_electrical.bo.custom;

import com.example.lihini_electrical.bo.SuperBO;
import com.example.lihini_electrical.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {
    ArrayList<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException;
    String generateSupplierId() throws SQLException, ClassNotFoundException;
    boolean deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException;
    boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException;
    boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException;
}
