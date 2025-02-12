package com.example.lihini_electrical.dao.custom.impl;

import com.example.lihini_electrical.dao.SQLUtil;
import com.example.lihini_electrical.dao.custom.PaymentDAO;
import com.example.lihini_electrical.dto.PaymentDTO;
import com.example.lihini_electrical.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public ArrayList<Payment> getAllPayments() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Payment");
        ArrayList<Payment> paymentDTOS = new ArrayList<>();

        while (rst.next()) {
            paymentDTOS.add(new Payment(rst.getString(1),
                    rst.getDouble(2),
                    rst.getString(3),
                    rst.getDate(4)));
        }
        return paymentDTOS;
    }

    @Override
    public String getNextPaymentId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select pay_id  from Payment order by pay_id  desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";    }

    @Override
    public boolean delete(String paymentId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Payment where pay_id =?", paymentId);
    }

    @Override
    public boolean save(Payment payment) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Payment values (?,?,?,?)",
                payment.getPaymentID(),
                payment.getAmount(),
                payment.getOrderItemName(),
                payment.getDate()
        );    }

    @Override
    public boolean update(Payment payment) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "update Payment set Amount =? ,order_item_name =? ,pay_date =? where pay_id =?",
                payment.getPaymentID(),
                payment.getAmount(),
                payment.getOrderItemName(),
                payment.getDate()
        );    }
}
