package com.example.lihini_electrical.bo.custom.impl;

import com.example.lihini_electrical.bo.custom.VehicleBO;
import com.example.lihini_electrical.dao.DAOFactory;
import com.example.lihini_electrical.dao.custom.VehicleDAO;
import com.example.lihini_electrical.dto.VehicleDTO;
import com.example.lihini_electrical.entity.Vehicle;
import com.example.lihini_electrical.tdm.VehicleTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleBOImpl implements VehicleBO {
    VehicleDAO vehicleDAO = (VehicleDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    @Override
    public ArrayList<String> getAllVehicleIds() throws SQLException, ClassNotFoundException {
        return vehicleDAO.getAllIds();
    }

    @Override
    public VehicleDTO searchVehicle(String selectedVehicleId) throws SQLException, ClassNotFoundException {
        Vehicle vehicle = vehicleDAO.search(selectedVehicleId);
        VehicleDTO vehicleDTO = new VehicleDTO(vehicle.getVehicleId(),vehicle.getType());
        return vehicleDTO;
    }

    @Override
    public ArrayList<VehicleDTO> getAllVehicles() throws SQLException, ClassNotFoundException {
        ArrayList<VehicleDTO> vehicleDTOArrayList = new ArrayList<>();
        ArrayList<Vehicle> vehicleDTOS = vehicleDAO.getAll();
        ObservableList<VehicleTM> vehicleTMS = FXCollections.observableArrayList();

        for (Vehicle vehicleDTO : vehicleDTOS) {
           vehicleDTOArrayList.add(new VehicleDTO(vehicleDTO.getVehicleId(),
                   vehicleDTO.getType()));
        }
        return vehicleDTOArrayList;
    }

    @Override
    public String generateVehicleId() throws SQLException, ClassNotFoundException {
        String nextVehicleId = vehicleDAO.generateId();
        return nextVehicleId;
    }

    @Override
    public boolean saveVehicle(VehicleDTO vehicleDTO) throws SQLException, ClassNotFoundException {
        return vehicleDAO.save(new Vehicle(vehicleDTO.getVehicleId(),vehicleDTO.getType()));
    }

    @Override
    public boolean deleteVehicle(String vehicleId) throws SQLException, ClassNotFoundException {
        return vehicleDAO.delete(vehicleId);
    }

    @Override
    public boolean updateVehicle(VehicleDTO vehicleDTO) throws SQLException, ClassNotFoundException {
        return vehicleDAO.update(new Vehicle(vehicleDTO.getVehicleId(),vehicleDTO.getType()));
    }
}
