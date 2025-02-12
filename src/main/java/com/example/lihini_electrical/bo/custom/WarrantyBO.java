package com.example.lihini_electrical.bo.custom;

import com.example.lihini_electrical.bo.SuperBO;
import com.example.lihini_electrical.dto.WarrantyDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface WarrantyBO extends SuperBO {
    ArrayList<WarrantyDTO> getAllWarranties() throws SQLException, ClassNotFoundException;
    String generateWarrantyId() throws SQLException, ClassNotFoundException;
    boolean deleteWarranty(String warrantyId) throws SQLException, ClassNotFoundException;
    boolean saveWarranty(WarrantyDTO warrantyDTO) throws SQLException, ClassNotFoundException;
    boolean updateWarranty(WarrantyDTO warrantyDTO) throws SQLException, ClassNotFoundException;
}
