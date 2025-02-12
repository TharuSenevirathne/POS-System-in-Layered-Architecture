package com.example.lihini_electrical.dao.custom.impl;

import com.example.lihini_electrical.dao.SQLUtil;
import com.example.lihini_electrical.dao.custom.InventoryDAO;
import com.example.lihini_electrical.dto.InventoryDTO;
import com.example.lihini_electrical.entity.Inventory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryDAOImpl implements InventoryDAO {
    @Override
    public ArrayList<Inventory> getAllInventories() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Inventory");
        ArrayList<Inventory> inventoryDTOS = new ArrayList<>();
        while (rst.next()) {
            inventoryDTOS.add(new Inventory(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)));
        }
        return inventoryDTOS;
    }

    @Override
    public String getNextInventoryId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select in_id  from Inventory order by in_id  desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i + 1;
            return String.format("I%03d", newIdIndex);
        }
        return "I001";
    }

    @Override
    public boolean delete(String inventoryId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Inventory where in_id =?", inventoryId);
    }

    @Override
    public boolean save(Inventory inventory) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Inventory values (?,?,?)",
                inventory.getInventoryId(),
                inventory.getType(),
                inventory.getStocklevel()
        );
    }

    @Override
    public boolean update(Inventory inventory) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "update Inventory set in_type =? ,stock_level =? where in_id=?",
                inventory.getInventoryId(),
                inventory.getType(),
                inventory.getStocklevel()
        );
    }
}
