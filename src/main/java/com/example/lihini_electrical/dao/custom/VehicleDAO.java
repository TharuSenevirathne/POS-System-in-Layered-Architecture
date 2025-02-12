package com.example.lihini_electrical.dao.custom;

import com.example.lihini_electrical.dao.CrudDAO;
import com.example.lihini_electrical.dto.VehicleDTO;
import com.example.lihini_electrical.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleDAO extends CrudDAO<Vehicle> {
    ArrayList<Vehicle> getAllVehicles() throws SQLException, ClassNotFoundException;
    String getNextVehicleId() throws SQLException, ClassNotFoundException;
    boolean save(Vehicle vehicle) throws SQLException, ClassNotFoundException;
    boolean update(Vehicle vehicle) throws SQLException, ClassNotFoundException;
    boolean delete(String vehicleId) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllVehicleIds() throws SQLException, ClassNotFoundException;
    VehicleDTO findById(String selectedVehicleId) throws SQLException, ClassNotFoundException;
}
