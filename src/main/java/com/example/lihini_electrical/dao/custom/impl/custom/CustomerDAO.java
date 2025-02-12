package com.example.lihini_electrical.dao.custom.impl.custom;

import com.example.lihini_electrical.dao.CrudDAO;
import com.example.lihini_electrical.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO {
    ArrayList<Customer> getAllCustomers() throws SQLException, ClassNotFoundException;
    String getNextCustomerId() throws SQLException, ClassNotFoundException;
    boolean save(Customer customer) throws SQLException, ClassNotFoundException;
    boolean delete(String customerId) throws SQLException,ClassNotFoundException;
    boolean update(Customer customer) throws SQLException, ClassNotFoundException;
}
