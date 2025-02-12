package com.example.lihini_electrical.dao.custom.impl;

import com.example.lihini_electrical.dao.SQLUtil;
import com.example.lihini_electrical.dao.custom.DepartmentDAO;
import com.example.lihini_electrical.entity.Department;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepartmentDAOImpl implements DepartmentDAO {
    @Override
    public ArrayList<Department> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Department");
        ArrayList<Department> departments = new ArrayList<>();
        while (rst.next()) {
            departments.add(new Department(rst.getString(1), rst.getString(2), rst.getString(3)));
        }
        return departments;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select dep_id   from Department order by dep_id   desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i + 1;
            return String.format("D%03d", newIdIndex);
        }
        return "D001";
    }

    @Override
    public boolean delete(String departmentId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Department where dep_id=?", departmentId);
    }

    @Override
    public boolean save(Department department) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Department values (?,?,?)",
                department.getDepartmentid(),
                department.getName(),
                department.getEmployeeid()
        );
    }

    @Override
    public boolean update(Department department) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "update Department set dep_name =? emp_id=? where dep_id=?",
                department.getDepartmentid(),
                department.getName(),
                department.getEmployeeid()
        );
    }

    @Override
    public Department search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }
}
