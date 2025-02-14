package com.example.lihini_electrical.dao.custom.impl;

import com.example.lihini_electrical.dao.SQLUtil;
import com.example.lihini_electrical.dao.custom.WarrantyDAO;
import com.example.lihini_electrical.entity.Delivery;
import com.example.lihini_electrical.entity.Warranty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WarrantyDAOImpl implements WarrantyDAO {

    @Override
    public ArrayList<Warranty> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Warranty");

        ArrayList<Warranty> warrantyDTOS = new ArrayList<>();

        while (rst.next()) {
            warrantyDTOS.add(new Warranty(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4)));
        }
        return warrantyDTOS;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select war_id  from Warranty order by war_id  desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i + 1;
            return String.format("W%03d", newIdIndex);
        }
        return "W001";    }

    @Override
    public boolean delete(String warrantyId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Warranty where war_id=?", warrantyId);
    }

    @Override
    public boolean save(Warranty warrantyDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Warranty values (?,?,?,?)",
                warrantyDTO.getWarrantyId(),
                warrantyDTO.getProductName(),
                warrantyDTO.getWarrantyPeriodTime(),
                warrantyDTO.getWarrantyStartDate()
        );
    }

    @Override
    public boolean update(Warranty warrantyDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "update Warranty set product_name =? ,warranty_period_time =? ," +
                        "warranty_Starting_Date =? where war_id =?",
                warrantyDTO.getWarrantyId(),
                warrantyDTO.getProductName(),
                warrantyDTO.getWarrantyPeriodTime(),
                warrantyDTO.getWarrantyStartDate()
        );
    }

    @Override
    public Warranty search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }
}
