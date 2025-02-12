package com.example.lihini_electrical.bo.custom.impl;

import com.example.lihini_electrical.bo.custom.DeliveryBO;
import com.example.lihini_electrical.entity.Delivery;
import com.example.lihini_electrical.dao.DAOFactory;
import com.example.lihini_electrical.dao.custom.DeliveryDAO;
import com.example.lihini_electrical.dto.DeliveryDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryBOImpl implements DeliveryBO {
    DeliveryDAO deliveryDAO = (DeliveryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DELIVERY);

    @Override
    public String generateDeliveryId() throws SQLException, ClassNotFoundException {
        String nextDeliveryId = deliveryDAO.generateId();
        return nextDeliveryId;
    }

    @Override
    public ArrayList<DeliveryDTO> getAllDeliveries() throws SQLException, ClassNotFoundException {
        ArrayList<DeliveryDTO> deliveryDTOArrayList = new ArrayList<>();
        ArrayList<Delivery> deliveries = deliveryDAO.getAll();

        for (Delivery delivery1 : deliveries) {
            deliveryDTOArrayList.add(new DeliveryDTO(delivery1.getDeliveryId(),delivery1.getAddress(),
                    delivery1.getDate(),delivery1.getVehicleId()));
        }
        return deliveryDTOArrayList;
    }

    @Override
    public boolean deleteDelivery(String deliveryId) throws SQLException, ClassNotFoundException {
        return deliveryDAO.delete(deliveryId);
    }

    @Override
    public boolean updateDelivery(DeliveryDTO deliveryDTO) throws SQLException, ClassNotFoundException {
        return deliveryDAO.update(new Delivery(deliveryDTO.getDeliveryId(),deliveryDTO.getAddress(),
                deliveryDTO.getDate(),deliveryDTO.getVehicleId()));
    }

    @Override
    public boolean saveDelivery(DeliveryDTO deliveryDTO) throws SQLException, ClassNotFoundException {
        return deliveryDAO.save(new Delivery(deliveryDTO.getDeliveryId(),deliveryDTO.getAddress(),
                deliveryDTO.getDate(),deliveryDTO.getVehicleId()));
    }
}
