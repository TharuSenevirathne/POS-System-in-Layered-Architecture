package com.example.lihini_electrical.bo.custom;

import com.example.lihini_electrical.bo.SuperBO;
import com.example.lihini_electrical.dto.WarehouseDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface WarehouseBO extends SuperBO {
    ArrayList<WarehouseDTO> getAllWarehouses() throws SQLException, ClassNotFoundException;
    String generateWarehouseId() throws SQLException, ClassNotFoundException;
    boolean deleteWarehouse(String warehouseId) throws SQLException, ClassNotFoundException;
    boolean saveWarehouse(WarehouseDTO warehouseDTO) throws SQLException, ClassNotFoundException;
    boolean updateWarehouse(WarehouseDTO warehouseDTO) throws SQLException, ClassNotFoundException;
}
