package com.example.lihini_electrical.bo.custom.impl;

import com.example.lihini_electrical.bo.custom.DepartmentBO;
import com.example.lihini_electrical.dao.DAOFactory;
import com.example.lihini_electrical.dao.custom.DepartmentDAO;
import com.example.lihini_electrical.dto.DepartmentDTO;
import com.example.lihini_electrical.entity.Department;
import com.example.lihini_electrical.tdm.DepartmentTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class DepartmentBOImpl implements DepartmentBO {
    DepartmentDAO departmentDAO = (DepartmentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DEPARTMENT);

    @Override
    public ArrayList<DepartmentDTO> getAllDepartments() throws SQLException, ClassNotFoundException {
        ArrayList<DepartmentDTO> departmentDTOArrayList = new ArrayList<>();
        ArrayList<Department> departments = departmentDAO.getAllDepartments();
        ObservableList<DepartmentTM> departmentTMS = FXCollections.observableArrayList();
        for (Department department1 : departments) {
            departmentDTOArrayList.add(new DepartmentDTO(department1.getDepartmentid(),
                    department1.getName(),
                    department1.getEmployeeid()));
        }
        return departmentDTOArrayList;
    }

    @Override
    public String getNextDepartmentId() throws SQLException, ClassNotFoundException {
        String nextDepartmentId = departmentDAO.getNextDepartmentId();
        return nextDepartmentId;
    }

    @Override
    public boolean deleteDepartment(String departmentId) throws SQLException, ClassNotFoundException {
        return departmentDAO.delete(departmentId);
    }

    @Override
    public boolean saveDepartment(DepartmentDTO departmentDTO) throws SQLException, ClassNotFoundException {
        return departmentDAO.save(new Department(departmentDTO.getDepartmentid(),departmentDTO.getName(),
                departmentDTO.getEmployeeid()));
    }

    @Override
    public boolean updateDepartment(DepartmentDTO departmentDTO) throws SQLException, ClassNotFoundException {
        return departmentDAO.update(new Department(departmentDTO.getDepartmentid(),departmentDTO.getName(),
                departmentDTO.getEmployeeid()));
    }
}
