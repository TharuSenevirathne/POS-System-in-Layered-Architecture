package com.example.lihini_electrical.bo.custom;

import com.example.lihini_electrical.bo.SuperBO;
import com.example.lihini_electrical.dto.PaymentDTO;
import com.example.lihini_electrical.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO {
    ArrayList<PaymentDTO> getAllPayments() throws SQLException, ClassNotFoundException;
    String getNextPaymentId() throws SQLException, ClassNotFoundException;
    boolean deletePayment(String paymentId) throws SQLException, ClassNotFoundException;
    boolean savePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;
    boolean updatePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;
}
