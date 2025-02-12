package com.example.lihini_electrical.dao.custom;

import com.example.lihini_electrical.dao.CrudDAO;
import com.example.lihini_electrical.entity.Warranty;

import java.sql.SQLException;
import java.util.ArrayList;

public interface WarrantyDAO extends CrudDAO<Warranty> {
    ArrayList<Warranty> getAllWarranties() throws SQLException, ClassNotFoundException;
    String getNextWarrantyId() throws SQLException, ClassNotFoundException;
    boolean delete(String warrantyId) throws SQLException;
    boolean save(Warranty warrantyDTO) throws SQLException;
    boolean update(Warranty warrantyDTO) throws SQLException;
}
