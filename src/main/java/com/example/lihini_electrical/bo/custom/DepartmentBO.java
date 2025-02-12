package com.example.lihini_electrical.bo.custom;

import com.example.lihini_electrical.bo.SuperBO;
import com.example.lihini_electrical.dto.DepartmentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DepartmentBO extends SuperBO {
    ArrayList<DepartmentDTO> getAllDepartments() throws SQLException, ClassNotFoundException;
    String getNextDepartmentId() throws SQLException, ClassNotFoundException;
    boolean deleteDepartment(String departmentId) throws SQLException, ClassNotFoundException;
    boolean saveDepartment(DepartmentDTO departmentDTO) throws SQLException, ClassNotFoundException;
    boolean updateDepartment(DepartmentDTO departmentDTO) throws SQLException, ClassNotFoundException;
}
