package com.example.lihini_electrical.dao.custom.impl;

import com.example.lihini_electrical.dao.SQLUtil;
import com.example.lihini_electrical.dao.custom.CustomerDAO;
import com.example.lihini_electrical.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Customer");
        ArrayList<Customer> customers = new ArrayList<>();

        while (rst.next()) {
            Customer  entity = new Customer(rst.getString("customer id"),rst.getString("name"),
                    rst.getString("address"),rst.getString("phone no"),rst.getString("email"),
                    rst.getString("type"),rst.getString("employee id"));
            customers.add(entity);
        }
        return customers;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select cust_id from Customer order by cust_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i + 1;
            return String.format("C%03d", newIdIndex);
        }
        return "C001";
    }

    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Customer values (?,?,?,?,?,?,?)",
                entity.getCustomerId(),
                entity.getName(),
                entity.getAddress(),
                entity.getPhoneNo(),
                entity.getEmail(),
                entity.getType(),
                entity.getEmployeeId()
        );
    }

    @Override
    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Customer where cust_id=?", customerId);
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "update Customer set cust_name=? ,cust_address=? ," +
                        "cust_phoneNo=? ,cust_email=? ,cust_type=? ,emp_id=? where cust_id=?",
                entity.getName(),
                entity.getAddress(),
                entity.getPhoneNo(),
                entity.getEmail(),
                entity.getType(),
                entity.getEmployeeId(),
                entity.getCustomerId()
        );
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select cust_id  from Customer");

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }
        return customerIds;
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Customer where cust_id=?", id);
       rst.next();
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );
    }
}
