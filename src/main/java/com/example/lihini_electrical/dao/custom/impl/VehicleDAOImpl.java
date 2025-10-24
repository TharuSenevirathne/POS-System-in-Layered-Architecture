package com.example.lihini_electrical.dao.custom.impl;

import com.example.lihini_electrical.dao.SQLUtil;
import com.example.lihini_electrical.dao.custom.VehicleDAO;
import com.example.lihini_electrical.entity.Vehicle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleDAOImpl implements VehicleDAO {
    @Override
    public ArrayList<Vehicle> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Vehicle");

        ArrayList<Vehicle> vehicleDTOS = new ArrayList<>();

        while (rst.next()) {
            vehicleDTOS.add(new Vehicle(rst.getString(1),
                    rst.getString(2))) ;
        }
        return vehicleDTOS;    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select  vehicle_id from Vehicle order by  vehicle_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i + 1;
            return String.format("V%03d", newIdIndex);
        }
        return "V001";    }

    @Override
    public boolean save(Vehicle vehicle) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Vehicle values (?,?)",
                vehicle.getVehicleId(),
                vehicle.getType()
        );
    }

    @Override
    public boolean update(Vehicle vehicle) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "update Vehicle set type  =? where  vehicle_id =?",
                vehicle.getType(),
                vehicle.getVehicleId()
        );
    }

    @Override
    public boolean delete(String vehicleId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Vehicle where  vehicle_id =?", vehicleId);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select  vehicle_id from Vehicle");

        ArrayList<String> vehicleIds = new ArrayList<>();

        while (rst.next()) {
            vehicleIds.add(rst.getString(1));
        }
        return vehicleIds;    }

    @Override
    public Vehicle search(String selectedVehicleId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Vehicle where  vehicle_id=?", selectedVehicleId);
        rst.next();
        return new Vehicle(selectedVehicleId, rst.getString("type"));

    }
}
