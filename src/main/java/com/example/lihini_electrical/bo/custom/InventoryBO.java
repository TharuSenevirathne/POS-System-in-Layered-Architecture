package com.example.lihini_electrical.bo.custom;

import com.example.lihini_electrical.bo.SuperBO;
import com.example.lihini_electrical.dto.InventoryDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InventoryBO extends SuperBO {
    ArrayList<InventoryDTO> getAllInventories() throws SQLException, ClassNotFoundException;
    String generateInventoryId() throws SQLException, ClassNotFoundException;
    boolean deleteInventory(String inventoryId) throws SQLException, ClassNotFoundException;
    boolean saveInventory(InventoryDTO inventoryDTO) throws SQLException, ClassNotFoundException;
    boolean updateInventory(InventoryDTO inventoryDTO) throws SQLException, ClassNotFoundException;
    String getAllInventoryIds() throws SQLException, ClassNotFoundException;
}
