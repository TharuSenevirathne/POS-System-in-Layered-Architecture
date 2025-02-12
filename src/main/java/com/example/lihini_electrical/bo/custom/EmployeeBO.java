package com.example.lihini_electrical.bo.custom;

import com.example.lihini_electrical.bo.SuperBO;
import com.example.lihini_electrical.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    ArrayList<EmployeeDTO> getAllEmployees() throws SQLException, ClassNotFoundException;
    String getNextEmployeeId() throws SQLException, ClassNotFoundException;
    boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;
    boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException;
    boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllEmployeeIds();
    EmployeeDTO findById(String selectedEmployeeId);
}
