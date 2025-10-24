package com.example.lihini_electrical.dao.custom.impl;

import com.example.lihini_electrical.dao.SQLUtil;
import com.example.lihini_electrical.dao.custom.OrdersDAO;
import com.example.lihini_electrical.dto.OrdersAndProductDetailsDTO;
import com.example.lihini_electrical.entity.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersDAOImpl implements OrdersDAO {

    @Override
    public Orders search(String orderId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Orders WHERE order_id=?", orderId);
        if (rst.next()) {
            return new Orders(
                    rst.getString("order_id"),
                    rst.getString("customer_id"),
                    rst.getDate("date")
            );
        }
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT order_id FROM Orders");
        ArrayList<String> orderIds = new ArrayList<>();
        while (rst.next()) {
            orderIds.add(rst.getString("order_id"));
        }
        return orderIds;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT order_id FROM Orders ORDER BY order_id DESC LIMIT 1");
        if (rst.next()) {
            String lastId = rst.getString(1);
            int i = Integer.parseInt(lastId.substring(1));
            return String.format("O%03d", i + 1);
        }
        return "O001";
    }

    @Override
    public boolean save(Orders order) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Orders VALUES (?,?,?)",
                order.getOrderId(),
                order.getCustomerId(),
                order.getDate()
        );
    }

    @Override
    public boolean saveOrderDetails(ArrayList<OrdersAndProductDetailsDTO> details) throws SQLException, ClassNotFoundException {
        for (OrdersAndProductDetailsDTO dto : details) {
            boolean saved = SQLUtil.execute(
                    "INSERT INTO OrdersAndProductDetails (order_id, product_id, cart_quantity, unit_price) VALUES (?,?,?,?)",
                    dto.getOrderId(),
                    dto.getProductId(),
                    dto.getCartQuantity(),
                    dto.getUnitprice()
            );
            if (!saved) return false;
        }
        return true;
    }


    @Override
    public boolean update(Orders order) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Orders SET customer_id=?, date=? WHERE order_id=?",
                order.getCustomerId(),
                order.getDate(),
                order.getOrderId()
        );
    }

    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Orders");
        ArrayList<Orders> list = new ArrayList<>();
        while (rst.next()) {
            list.add(new Orders(
                    rst.getString("order_id"),
                    rst.getString("customer_id"),
                    rst.getDate("date")
            ));
        }
        return list;
    }

    @Override
    public boolean delete(String orderId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM Orders WHERE order_id=?", orderId);
    }

    @Override
    public ArrayList<OrdersAndProductDetailsDTO> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute(
                "SELECT order_id, product_id, cart_quantity, unit_price FROM OrdersAndProductDetails WHERE order_id=?",
                orderId
        );

        ArrayList<OrdersAndProductDetailsDTO> list = new ArrayList<>();
        while (rst.next()) {
            // Use exact DB column names
            list.add(new OrdersAndProductDetailsDTO(
                    rst.getString("order_id"),
                    rst.getString("product_id"),
                    rst.getInt("cart_quantity"),  // assuming integer quantity in cart
                    rst.getDouble("unit_price")
            ));
        }
        return list;
    }

}
