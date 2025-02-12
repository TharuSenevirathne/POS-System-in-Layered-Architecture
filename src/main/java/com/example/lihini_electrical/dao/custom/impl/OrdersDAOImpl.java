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
    public ArrayList<String> getAllOrderIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select ord_id from Orders");

        ArrayList<String> orderIds = new ArrayList<>();

        while (rst.next()) {
            orderIds.add(rst.getString(1));
        }
        return orderIds;
    }

    @Override
    public OrdersDTO findById(String selectedOrderId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Orders where ord_id=?", selectedOrderId);

        if (rst.next()) {
            return new OrdersDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3)
            );
        }
        return null;
    }

    @Override
    public String getNextOrderId() throws SQLException, ClassNotFoundException {
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
}
