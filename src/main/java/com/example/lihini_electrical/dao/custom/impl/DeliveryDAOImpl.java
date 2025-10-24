package com.example.lihini_electrical.dao.custom.impl;

import com.example.lihini_electrical.entity.Customer;
import com.example.lihini_electrical.entity.Delivery;
import com.example.lihini_electrical.dao.SQLUtil;
import com.example.lihini_electrical.dao.custom.DeliveryDAO;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryDAOImpl implements DeliveryDAO {
    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select delivery_id   from Delivery order by delivery_id   desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i + 1;
            return String.format("D%03d", newIdIndex);
        }
        return "D001";
    }

    @Override
    public ArrayList<Delivery> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Delivery");
        ArrayList<Delivery> deliveries = new ArrayList<>();

        while (rst.next()) {
            Delivery entity = new Delivery(rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getString(4));
            deliveries.add(entity);
        }
        return deliveries;
    }

    @Override
    public boolean delete(String deliveryId) throws SQLException, ClassNotFoundException {
         return SQLUtil.execute("delete from Delivery where delivery_id =?", deliveryId);
    }

    @Override
    public boolean save(Delivery entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Delivery values (?,?,?,?)",
                entity.getDeliveryId(),
                entity.getAddress(),
                entity.getDate(),
                entity.getVehicleId()
        );
    }

    @Override
    public boolean update(Delivery entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update Delivery set address =? , delivery_date=?,vehicle_id =? where delivery_id  =?",
                entity.getAddress(),
                entity.getDate(),
                entity.getVehicleId(),
                entity.getDeliveryId()
        );
    }

    @Override
    public Delivery search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Delivery where delivery_id =?", id);
        rst.next();
        return new Delivery(
                rst.getString(1),
                rst.getString(2),
                rst.getDate(3),
                rst.getString(4)
        );
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select delivery_id   from Delivery");
        ArrayList<String> deliveryIds = new ArrayList<>();

        while (rst.next()) {
            deliveryIds.add(rst.getString(1));
        }
        return deliveryIds;    }
}
