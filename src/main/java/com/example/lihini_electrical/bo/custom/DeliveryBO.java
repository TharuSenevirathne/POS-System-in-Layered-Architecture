package com.example.lihini_electrical.bo.custom;

import com.example.lihini_electrical.bo.SuperBO;
import com.example.lihini_electrical.dto.DeliveryDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DeliveryBO extends SuperBO {
    String generateDeliveryId() throws SQLException, ClassNotFoundException;
    ArrayList<DeliveryDTO> getAllDeliveries() throws SQLException, ClassNotFoundException;
    boolean deleteDelivery(String deliveryId) throws SQLException, ClassNotFoundException;
    boolean updateDelivery(DeliveryDTO dto) throws SQLException, ClassNotFoundException;
    boolean saveDelivery(DeliveryDTO dto) throws SQLException, ClassNotFoundException;
}
