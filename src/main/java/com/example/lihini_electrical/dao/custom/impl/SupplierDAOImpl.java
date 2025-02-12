package com.example.lihini_electrical.dao.custom.impl;

import com.example.lihini_electrical.dao.SQLUtil;
import com.example.lihini_electrical.dao.custom.impl.custom.SupplierDAO;
import com.example.lihini_electrical.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Supplier");
        ArrayList<Supplier> supplier = new ArrayList<>();
        while (rst.next()) {
            supplier.add(new Supplier(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)));
        }
        return supplier;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select sup_id  from Supplier order by sup_id  desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i + 1;
            return String.format("S%03d", newIdIndex);
        }
        return "S001";
    }

    @Override
    public boolean delete(String supplierId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Supplier where sup_id=?", supplierId);
    }

    @Override
    public boolean save(Supplier supplier) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Supplier values (?,?,?,?)",
                supplier.getSupplierId(),
                supplier.getName(),
                supplier.getBrand(),
                supplier.getPhoneNo()
        );    }

    @Override
    public boolean update(Supplier supplier) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "update Supplier set sup_name =? ,brand=? ,sup_phoneNo =? where sup_id=?",
                supplier.getSupplierId(),
                supplier.getName(),
                supplier.getBrand(),
                supplier.getPhoneNo()
        );    }

    @Override
    public Supplier search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Supplier> getAllSuppliers() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getNextSupplierId() throws SQLException, ClassNotFoundException {
        return "";
    }
}
