package com.example.lihini_electrical.dao.custom;

import com.example.lihini_electrical.dao.CrudDAO;
import com.example.lihini_electrical.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentDAO extends CrudDAO<Payment> {
    ArrayList<Payment> getAllPayments() throws SQLException, ClassNotFoundException;
    String getNextPaymentId() throws SQLException, ClassNotFoundException;
    boolean delete(String paymentId) throws SQLException, ClassNotFoundException;
    boolean save(Payment payment) throws SQLException, ClassNotFoundException;
    boolean update(Payment payment) throws SQLException, ClassNotFoundException;
}
