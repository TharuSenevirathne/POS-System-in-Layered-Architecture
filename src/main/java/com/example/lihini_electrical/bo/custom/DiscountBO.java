package com.example.lihini_electrical.bo.custom;

import com.example.lihini_electrical.bo.SuperBO;
import com.example.lihini_electrical.dto.DiscountDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DiscountBO extends SuperBO {
    ArrayList<DiscountDTO> getAllDiscounts() throws SQLException, ClassNotFoundException;
    String getNextDiscountId() throws SQLException, ClassNotFoundException;
    boolean deleteDiscount(String discountId) throws SQLException, ClassNotFoundException;
    boolean saveDiscount(DiscountDTO discountDTO) throws SQLException, ClassNotFoundException;
    boolean updateDiscount(DiscountDTO discountDTO) throws SQLException, ClassNotFoundException;
}
