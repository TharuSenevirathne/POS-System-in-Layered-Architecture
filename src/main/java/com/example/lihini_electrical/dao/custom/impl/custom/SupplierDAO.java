package com.example.lihini_electrical.dao.custom.impl.custom;

import com.example.lihini_electrical.dao.CrudDAO;
import com.example.lihini_electrical.entity.Delivery;
import com.example.lihini_electrical.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierDAO extends CrudDAO<Supplier> {
    ArrayList<Supplier> getAllSuppliers() throws SQLException, ClassNotFoundException;
    String getNextSupplierId() throws SQLException, ClassNotFoundException;
    boolean delete(String supplierId) throws SQLException, ClassNotFoundException;
    boolean save(Delivery supplier) throws SQLException, ClassNotFoundException;
    boolean update(Supplier supplier) throws SQLException, ClassNotFoundException;
}
