package com.example.lihini_electrical.dao.custom;

import com.example.lihini_electrical.dao.CrudDAO;
import com.example.lihini_electrical.dto.EmployeeDTO;
import com.example.lihini_electrical.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDAO extends CrudDAO {
    ArrayList<Employee> getAllEmployees() throws SQLException, ClassNotFoundException;
    String getNextEmployeeId() throws SQLException, ClassNotFoundException;
    boolean delete(String employeeId) throws SQLException;
    boolean save(Employee employee) throws SQLException, ClassNotFoundException;
    boolean update(Employee employee) throws SQLException, ClassNotFoundException;
    EmployeeDTO findById(String selectedEmployeeId);
}
