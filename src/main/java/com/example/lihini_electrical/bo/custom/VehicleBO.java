package com.example.lihini_electrical.bo.custom;

import com.example.lihini_electrical.bo.SuperBO;
import com.example.lihini_electrical.dto.VehicleDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleBO extends SuperBO {
    ArrayList<String> getAllVehicleIds() throws SQLException, ClassNotFoundException;
    VehicleDTO searchVehicle(String selectedVehicleId) throws SQLException, ClassNotFoundException;
    ArrayList<VehicleDTO> getAllVehicles() throws SQLException, ClassNotFoundException;
    String generateVehicleId() throws SQLException, ClassNotFoundException;
    boolean saveVehicle(VehicleDTO vehicleDTO) throws SQLException, ClassNotFoundException;
    boolean deleteVehicle(String vehicleId) throws SQLException, ClassNotFoundException;
    boolean updateVehicle(VehicleDTO vehicleDTO) throws SQLException, ClassNotFoundException;
}
