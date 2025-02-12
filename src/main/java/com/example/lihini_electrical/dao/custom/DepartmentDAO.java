package com.example.lihini_electrical.dao.custom;

import com.example.lihini_electrical.dao.CrudDAO;
import com.example.lihini_electrical.entity.Department;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DepartmentDAO extends CrudDAO<Department> {
    ArrayList<Department> getAllDepartments() throws SQLException, ClassNotFoundException;
    String getNextDepartmentId() throws SQLException, ClassNotFoundException;
    boolean delete(String departmentId) throws SQLException;
    boolean save(Department department) throws SQLException;
    boolean update(Department department) throws SQLException;
}
