package com.example.lihini_electrical.dao.custom;

import com.example.lihini_electrical.dao.CrudDAO;
import com.example.lihini_electrical.entity.Inventory;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InventoryDAO extends CrudDAO {
    ArrayList<Inventory> getAllInventories() throws SQLException, ClassNotFoundException;
    String getNextInventoryId() throws SQLException, ClassNotFoundException;
    boolean delete(String inventoryId) throws SQLException;
    boolean save(Inventory inventory) throws SQLException, ClassNotFoundException;
    boolean update(Inventory inventory) throws SQLException, ClassNotFoundException;
}
