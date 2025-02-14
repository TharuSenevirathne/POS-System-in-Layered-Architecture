package com.example.lihini_electrical.dao.custom.impl;

import com.example.lihini_electrical.dao.SQLUtil;
import com.example.lihini_electrical.dao.custom.DiscountDAO;
import com.example.lihini_electrical.entity.Delivery;
import com.example.lihini_electrical.entity.Discount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiscountDAOImpl implements DiscountDAO {
    @Override
    public ArrayList<Discount> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Discount");

        ArrayList<Discount> discounts = new ArrayList<>();

        while (rst.next()) {
            discounts.add(new Discount(rst.getString(1),rst.getDouble(2),
                    rst.getString(3)));
        }
        return discounts;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select dis_id   from Discount order by dis_id   desc limit 1");

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
    public boolean delete(String discountId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Discount where dis_id=?", discountId);
    }

    @Override
    public boolean save(Discount discount) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Discount values (?,?,?)",
                discount.getDiscountid(),
                discount.getAmount(),
                discount.getOrderid()
        );
    }

    @Override
    public boolean update(Discount discount) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "update Discount set amount=? ,ord_id =? where dis_id=?",
                discount.getDiscountid(),
                discount.getAmount(),
                discount.getOrderid()
        );
    }

    @Override
    public Discount search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }

}
