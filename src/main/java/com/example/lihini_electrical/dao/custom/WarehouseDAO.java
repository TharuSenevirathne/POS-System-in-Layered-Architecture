package com.example.lihini_electrical.dao.custom;

import com.example.lihini_electrical.dao.CrudDAO;
import com.example.lihini_electrical.entity.Warehouse;

import java.sql.SQLException;
import java.util.ArrayList;

public interface WarehouseDAO extends CrudDAO<Warehouse> {
    ArrayList<Warehouse> getAllWarehouses() throws SQLException, ClassNotFoundException;
    String getNextWarehouseId() throws SQLException, ClassNotFoundException;
    boolean delete(String warehouseId) throws SQLException, ClassNotFoundException;
    boolean save(Warehouse warehouse) throws SQLException, ClassNotFoundException;
    boolean update(Warehouse warehouse) throws SQLException, ClassNotFoundException;
}
