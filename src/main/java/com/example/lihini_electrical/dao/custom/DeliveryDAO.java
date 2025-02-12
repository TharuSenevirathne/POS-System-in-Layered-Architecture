package com.example.lihini_electrical.dao.custom;

import com.example.lihini_electrical.dao.CrudDAO;
import com.example.lihini_electrical.entity.Delivery;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DeliveryDAO extends CrudDAO {
    String getNextDeliveryId() throws SQLException, ClassNotFoundException;
    ArrayList<Delivery> getAllDeliveries() throws SQLException, ClassNotFoundException;
    boolean delete(String deliveryId) throws SQLException, ClassNotFoundException;
    boolean save(Delivery delivery) throws SQLException, ClassNotFoundException;
    boolean update(Delivery delivery) throws SQLException, ClassNotFoundException;

}
