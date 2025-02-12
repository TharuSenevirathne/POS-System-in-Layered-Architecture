package com.example.lihini_electrical.bo.custom.impl;

import com.example.lihini_electrical.bo.custom.WarrantyBO;
import com.example.lihini_electrical.dao.DAOFactory;
import com.example.lihini_electrical.dao.custom.WarrantyDAO;
import com.example.lihini_electrical.dto.WarrantyDTO;
import com.example.lihini_electrical.entity.Warranty;
import com.example.lihini_electrical.tdm.WarrantyTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class WarrantyBOImpl implements WarrantyBO {
    WarrantyDAO warrantyDAO = (WarrantyDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.WARRANTY);

    @Override
    public ArrayList<WarrantyDTO> getAllWarranties() throws SQLException, ClassNotFoundException {
        ArrayList<WarrantyDTO> warrantyDTOArrayList = new ArrayList<>();
        ArrayList<Warranty> warrantyDTOS = warrantyDAO.getAll();

        ObservableList<WarrantyTM> warrantyTMS = FXCollections.observableArrayList();

        for (Warranty warrantyDTO : warrantyDTOS) {
            warrantyDTOArrayList.add(new WarrantyDTO(warrantyDTO.getWarrantyId(),
                    warrantyDTO.getProductName(),
                    warrantyDTO.getWarrantyPeriodTime(),
                    warrantyDTO.getWarrantyStartDate()));
        }
        return warrantyDTOArrayList;
    }

    @Override
    public String generateWarrantyId() throws SQLException, ClassNotFoundException {
        String nextWarrantyId = warrantyDAO.generateId();
        return nextWarrantyId;
    }

    @Override
    public boolean deleteWarranty(String warrantyId) throws SQLException, ClassNotFoundException {
        return warrantyDAO.delete(warrantyId);
    }

    @Override
    public boolean saveWarranty(WarrantyDTO warrantyDTO) throws SQLException, ClassNotFoundException {
        return warrantyDAO.save(new Warranty(warrantyDTO.getWarrantyId(),warrantyDTO.getProductName(),
                warrantyDTO.getWarrantyPeriodTime(),warrantyDTO.getWarrantyStartDate()));
    }

    @Override
    public boolean updateWarranty(WarrantyDTO warrantyDTO) throws SQLException, ClassNotFoundException {
        return warrantyDAO.update(new Warranty(warrantyDTO.getWarrantyId(),warrantyDTO.getProductName(),
                warrantyDTO.getWarrantyPeriodTime(),warrantyDTO.getWarrantyStartDate()));    }
}
