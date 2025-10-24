package com.example.lihini_electrical.dao.custom.impl;

import com.example.lihini_electrical.dao.SQLUtil;
import com.example.lihini_electrical.dao.custom.InventoryDAO;
import com.example.lihini_electrical.entity.Customer;
import com.example.lihini_electrical.entity.Inventory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryDAOImpl implements InventoryDAO {
    @Override
    public ArrayList<Inventory> getAll() throws SQLException, ClassNotFoundException {
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
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select inventory_id   from Inventory order by inventory_id   desc limit 1");

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
        return SQLUtil.execute("delete from Inventory where inventory_id  =?", inventoryId);
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
        return SQLUtil.execute( "update Inventory set type =? ,stock_level =? where inventory_id=?",
                inventory.getType(),
                inventory.getStocklevel(),
                inventory.getInventoryId()
        );
    }

    @Override
    public Inventory search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Inventory where inventory_id =?", id);
        rst.next();
        return new Inventory(
                rst.getString(1),
                rst.getString(2),
                rst.getString(3)
        );
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Inventory");
        ArrayList<String> inventories = new ArrayList<>();

        while (rst.next()) {
            inventories.add(rst.getString("inventory_id"));
        }
        return inventories;
    }

}
