package com.example.lihini_electrical.dao.custom.impl;

import com.example.lihini_electrical.dao.SQLUtil;
import com.example.lihini_electrical.dao.custom.WarehouseDAO;
import com.example.lihini_electrical.entity.Customer;
import com.example.lihini_electrical.entity.Warehouse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WarehouseDAOImpl implements WarehouseDAO {
    @Override
    public ArrayList<Warehouse> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Warehouse");

        ArrayList<Warehouse> warehouseDTOS = new ArrayList<>();

        while (rst.next()) {
            Warehouse warehouseDTO = new Warehouse(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
            warehouseDTOS.add(warehouseDTO);
        }
        return warehouseDTOS;    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select  warehouse_id  from Warehouse order by  warehouse_id  desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i + 1;
            return String.format("W%03d", newIdIndex);
        }
        return "W001";
    }

    @Override
    public boolean delete(String warehouseId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Warehouse where  warehouse_id=?", warehouseId);
    }

    @Override
    public boolean save(Warehouse warehouse) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Warehouse values (?,?,?)",
                warehouse.getWarehouseId(),
                warehouse.getName(),
                warehouse.getLocation()
        );    }

    @Override
    public boolean update(Warehouse warehouse) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "update Warehouse set name =? ,location  =? ,where  warehouse_id=?",
                warehouse.getName(),
                warehouse.getLocation(),
                warehouse.getWarehouseId()
        );    }

    @Override
    public Warehouse search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Warehouse where  warehouse_id=?", id);
        rst.next();
        return new Warehouse(
                rst.getString(1),
                rst.getString(2),
                rst.getString(3)
        );
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select  warehouse_id  from Warehouse");
        ArrayList<String> warehouseIds = new ArrayList<>();

        while (rst.next()) {
            warehouseIds.add(rst.getString(1));
        }
        return warehouseIds;
    }
}
