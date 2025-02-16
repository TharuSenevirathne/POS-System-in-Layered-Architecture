package com.example.lihini_electrical.dao.custom.impl;

import com.example.lihini_electrical.dao.SQLUtil;
import com.example.lihini_electrical.dao.custom.OrdersDAO;
import com.example.lihini_electrical.entity.Orders;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersDAOImpl implements OrdersDAO {

    @Override
    public Orders search(String selectedOrderId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Orders where ord_id=?", selectedOrderId);

        if (rst.next()) {
            return new Orders(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3)
            );
        }
        return null;    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select ord_id from Orders");

        ArrayList<String> orderIds = new ArrayList<>();

        while (rst.next()) {
            orderIds.add(rst.getString(1));
        }
        return orderIds;
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
    public boolean update(Orders dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean save(Orders dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Orders values (?,?,?)",
                dto.getOrderId(),
                dto.getCustomerId(),
                dto. getDate()
        );
    }

    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

        @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
