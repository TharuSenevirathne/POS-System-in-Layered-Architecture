package com.example.lihini_electrical.dao.custom.impl;

import com.example.lihini_electrical.dao.SQLUtil;
import com.example.lihini_electrical.dao.custom.EmployeeDAO;
import com.example.lihini_electrical.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Employee");

        ArrayList<Employee> employeeDTOS = new ArrayList<>();

        while (rst.next()){
            employeeDTOS.add(new Employee(rst.getString(1),rst.getString(2),
                    rst.getString(3),rst.getString(4),rst.getString(5)));
        }
        return employeeDTOS;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select emp_id  from Employee order by emp_id  desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i + 1;
            return String.format("E%03d", newIdIndex);
        }
        return "E001";
    }

    @Override
    public boolean delete(String employeeId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Employee where emp_id=?", employeeId);
    }

    @Override
    public boolean save(Employee employee) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Employee values (?,?,?,?,?)",
                employee.getEmployeeId(),
                employee.getName(),
                employee.getAddress(),
                employee.getPosition(),
                employee.getPhoneNumber()
        );
    }

    @Override
    public boolean update(Employee employee) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "update Employee set emp_name =? ,emp_address=? ," +
                        "emp_position=? ,emp_phoneNo =?  where emp_id=?",
                employee.getEmployeeId(),
                employee.getName(),
                employee.getAddress(),
                employee.getPosition(),
                employee.getPhoneNumber()
        );
    }

    @Override
    public Employee search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }
}
