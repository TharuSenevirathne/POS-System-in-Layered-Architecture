package com.example.lihini_electrical.bo.custom.impl;

import com.example.lihini_electrical.bo.custom.DiscountBO;
import com.example.lihini_electrical.dao.DAOFactory;
import com.example.lihini_electrical.dao.custom.DiscountDAO;
import com.example.lihini_electrical.dto.DiscountDTO;
import com.example.lihini_electrical.entity.Discount;
import com.example.lihini_electrical.tdm.DiscountTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class DiscountBOImpl implements DiscountBO {
    DiscountDAO discountDAO = (DiscountDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DISCOUNT);

    @Override
    public ArrayList<DiscountDTO> getAllDiscounts() throws SQLException, ClassNotFoundException {
        ArrayList<DiscountDTO> discountDTOArrayList = new ArrayList<>();
        ArrayList<Discount> discounts = discountDAO.getAllDiscounts();
        ObservableList<DiscountTM> discountTMS = FXCollections.observableArrayList();
        for (Discount discount : discounts) {
            discountDTOArrayList.add(new DiscountDTO(discount.getDiscountid(),
                    discount.getAmount(),discount.getOrderid()));
        }
        return discountDTOArrayList;
    }

    @Override
    public String getNextDiscountId() throws SQLException, ClassNotFoundException {
        String nextDiscountId = discountDAO.getNextDiscountId();
        return nextDiscountId;
    }

    @Override
    public boolean deleteDiscount(String discountId) throws SQLException, ClassNotFoundException {
        return discountDAO.delete(discountId);
    }

    @Override
    public boolean saveDiscount(DiscountDTO discountDTO) throws SQLException, ClassNotFoundException {
        return discountDAO.save(new Discount(discountDTO.getDiscountid(),discountDTO.getAmount(),
                discountDTO.getOrderid()));
    }

    @Override
    public boolean updateDiscount(DiscountDTO discountDTO) throws SQLException, ClassNotFoundException {
        return discountDAO.update(new Discount(discountDTO.getDiscountid(),discountDTO.getAmount(),
                discountDTO.getOrderid()));
    }
}
