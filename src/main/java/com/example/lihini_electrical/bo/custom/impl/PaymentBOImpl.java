package com.example.lihini_electrical.bo.custom.impl;

import com.example.lihini_electrical.bo.BOFactory;
import com.example.lihini_electrical.bo.custom.PaymentBO;
import com.example.lihini_electrical.dao.custom.PaymentDAO;
import com.example.lihini_electrical.dto.PaymentDTO;
import com.example.lihini_electrical.entity.Payment;
import com.example.lihini_electrical.tdm.PaymentTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = (PaymentDAO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);

    @Override
    public ArrayList<PaymentDTO> getAllPayments() throws SQLException, ClassNotFoundException {
        ArrayList<PaymentDTO> PaymentDTOArrayList = new ArrayList<>();
        ArrayList<Payment> paymentDTOS = paymentDAO.getAllPayments();

        ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

        for (Payment paymentDTO : paymentDTOS) {
         PaymentDTOArrayList.add(new PaymentDTO(paymentDTO.getPaymentID(),
                 paymentDTO.getAmount(),
                 paymentDTO.getOrderItemName(),
                 paymentDTO.getDate()))  ;
        }
        return PaymentDTOArrayList;
    }

    @Override
    public String getNextPaymentId() throws SQLException, ClassNotFoundException {
        String nextPaymentId = paymentDAO.getNextPaymentId();
        return nextPaymentId;
    }

    @Override
    public boolean deletePayment(String paymentId) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(paymentId);
    }

    @Override
    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(new Payment(paymentDTO.getPaymentID(),
                paymentDTO.getAmount(),paymentDTO.getOrderItemName(),paymentDTO.getDate()));
    }

    @Override
    public boolean updatePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return paymentDAO.update(new Payment(paymentDTO.getPaymentID(),
                paymentDTO.getAmount(),paymentDTO.getOrderItemName(),paymentDTO.getDate()));
    }
}
