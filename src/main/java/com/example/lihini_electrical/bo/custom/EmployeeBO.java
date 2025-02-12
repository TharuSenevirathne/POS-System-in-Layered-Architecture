package com.example.lihini_electrical.bo.custom;

import com.example.lihini_electrical.bo.SuperBO;
import com.example.lihini_electrical.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    ArrayList<EmployeeDTO> getAllEmployees() throws SQLException, ClassNotFoundException;
    String generateEmployeeId() throws SQLException, ClassNotFoundException;
    boolean saveEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException;
    boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException;
    boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException;
    EmployeeDTO searchEmployee(String selectedEmployeeId) throws SQLException, ClassNotFoundException;
}
