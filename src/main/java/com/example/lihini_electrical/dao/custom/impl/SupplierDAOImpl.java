package com.example.lihini_electrical.dao.custom.impl;

import com.example.lihini_electrical.dao.CrudDAO;
import com.example.lihini_electrical.dao.SQLUtil;
import com.example.lihini_electrical.dao.custom.SupplierDAO;
import com.example.lihini_electrical.entity.Customer;
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
        ResultSet rst = SQLUtil.execute("select supplier_id  from Supplier order by supplier_id  desc limit 1");

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
    public boolean save(Supplier supplier) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Supplier values (?,?,?,?)",
                supplier.getSupplierId(),
                supplier.getName(),
                supplier.getBrand(),
                supplier.getPhoneNo()
        );    }

    @Override
    public boolean update(Supplier supplier) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "update Supplier set name =? ,brand=? ,phone_no =? where supplier_id=?",
                supplier.getName(),
                supplier.getBrand(),
                supplier.getPhoneNo(),
                supplier.getSupplierId()
        );    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Supplier where supplier_id=?", id);
    }

    @Override
    public Supplier search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Supplier where supplier_id=?", id);
        rst.next();
        return new Supplier(
                rst.getString(1),
                rst.getString(2),
                rst.getString(3),
                rst.getString(4)
        );
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select supplier_id  from Supplier");
        ArrayList<String> supplierIds = new ArrayList<>();

        while (rst.next()) {
            supplierIds.add(rst.getString(1));
        }
        return supplierIds;    }

}
