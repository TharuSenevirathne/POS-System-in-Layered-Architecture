package com.example.lihini_electrical.dao.custom;

import com.example.lihini_electrical.dao.CrudDAO;
import com.example.lihini_electrical.entity.Supplier;

import java.util.ArrayList;

public interface SupplierDAO extends CrudDAO<Supplier> {
    ArrayList<Supplier> getAllSuppliers();
    String getNextSupplierId();
    boolean save(Supplier supplier);
    boolean update(Supplier supplier);
    boolean delete(String supplierId);
}
