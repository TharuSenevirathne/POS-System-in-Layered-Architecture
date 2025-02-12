package com.example.lihini_electrical.dao.custom;

import com.example.lihini_electrical.dao.CrudDAO;
import com.example.lihini_electrical.entity.Discount;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DiscountDAO extends CrudDAO<Discount> {
    ArrayList<Discount> getAllDiscounts() throws SQLException, ClassNotFoundException;
    String getNextDiscountId() throws SQLException, ClassNotFoundException;
    boolean delete(String discountId) throws SQLException, ClassNotFoundException;
    boolean save(Discount discount) throws SQLException, ClassNotFoundException;
    boolean update(Discount discount) throws SQLException, ClassNotFoundException;
}
