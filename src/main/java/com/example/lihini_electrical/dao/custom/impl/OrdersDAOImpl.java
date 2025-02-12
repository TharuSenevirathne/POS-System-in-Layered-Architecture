package com.example.lihini_electrical.dao.custom.impl;

import com.example.lihini_electrical.controller.Orders;
import com.example.lihini_electrical.dao.SQLUtil;
import com.example.lihini_electrical.dao.custom.OrdersDAO;
import com.example.lihini_electrical.dto.OrdersDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersDAOImpl implements OrdersDAO {

    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Orders search(String selectedOrderId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select ord_id  from Orders  order by ord_id  desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i + 1;
            return String.format("O%03d", newIdIndex);
        }
        return "O001";    }

    @Override
    public boolean save(Orders orders) {
        return SQLUtil.execute("insert into Orders values (?,?,?)",
                orders.getOrderId(),
                orders.getCustomerId(),
                orders. getDate()
        );
    }

    @Override
    public boolean update(Orders dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
