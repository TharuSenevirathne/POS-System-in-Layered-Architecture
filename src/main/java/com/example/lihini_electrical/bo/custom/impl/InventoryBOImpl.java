package com.example.lihini_electrical.bo.custom.impl;

import com.example.lihini_electrical.bo.custom.InventoryBO;
import com.example.lihini_electrical.dao.DAOFactory;
import com.example.lihini_electrical.dao.custom.InventoryDAO;
import com.example.lihini_electrical.dto.InventoryDTO;
import com.example.lihini_electrical.entity.Inventory;
import com.example.lihini_electrical.tdm.InventoryTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
public class InventoryBOImpl implements InventoryBO {
    InventoryDAO inventoryDAO = (InventoryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.INVENTORY);

    @Override
    public ArrayList<InventoryDTO> getAllInventories() throws SQLException, ClassNotFoundException {
        ArrayList<InventoryDTO> inventoryDTOArrayList = new ArrayList<>();
        ArrayList<Inventory> inventory = inventoryDAO.getAllInventories();

        ObservableList<InventoryTM> inventoryTMS = FXCollections.observableArrayList();

        for (Inventory inventory1 : inventory) {
            inventoryDTOArrayList.add(new InventoryDTO(inventory1.getInventoryId(),
                    inventory1.getType(),
                    inventory1.getStocklevel()));
        }
        return inventoryDTOArrayList;
    }

    @Override
    public String getNextInventoryId() throws SQLException, ClassNotFoundException {
        String nextInventorytId = inventoryDAO.getNextInventoryId();
        return nextInventorytId;
    }

    @Override
    public boolean deleteInventory(String inventoryId) throws SQLException, ClassNotFoundException {
        return inventoryDAO.delete(inventoryId);
    }

    @Override
    public boolean saveInventory(InventoryDTO inventoryDTO) throws SQLException, ClassNotFoundException {
        return inventoryDAO.save(new Inventory(inventoryDTO.getInventoryId(),
                inventoryDTO.getType(),inventoryDTO.getStocklevel()));
    }

    @Override
    public boolean updateInventory(InventoryDTO inventoryDTO) throws SQLException, ClassNotFoundException {
        return  inventoryDAO.update(new Inventory(inventoryDTO.getInventoryId(),
                inventoryDTO.getType(),inventoryDTO.getStocklevel()));
    }

    @Override
    public String getAllInventoryIds() throws SQLException, ClassNotFoundException {
        String nextInventorytId = inventoryDAO.getNextInventoryId();
        return nextInventorytId;
    }
}
