package com.example.lihini_electrical.bo.custom.impl;

import com.example.lihini_electrical.bo.custom.EmployeeBO;
import com.example.lihini_electrical.dao.DAOFactory;
import com.example.lihini_electrical.dao.custom.EmployeeDAO;
import com.example.lihini_electrical.dto.EmployeeDTO;
import com.example.lihini_electrical.entity.Employee;
import com.example.lihini_electrical.tdm.EmployeeTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public ArrayList<EmployeeDTO> getAllEmployees() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDTO> employeeDTOArrayList = new ArrayList<>();
        ArrayList<Employee> employees = employeeDAO.getAll();
        ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList();

        for (Employee employee : employees) {
            employeeDTOArrayList.add(new EmployeeDTO(employee.getEmployeeId(),employee.getName(),
                    employee.getName(), employee.getPosition(),employee.getPhoneNumber()));
        }
        return employeeDTOArrayList;
    }

    @Override
    public String generateEmployeeId() throws SQLException, ClassNotFoundException {
        String nextEmployeeId = employeeDAO.generateId();
        return nextEmployeeId;
    }

    @Override
    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(employeeDTO.getEmployeeId(),employeeDTO.getName(),
                employeeDTO.getAddress(),employeeDTO.getPosition(),employeeDTO.getPhoneNumber()));
    }

    @Override
    public boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(employeeId);
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(employeeDTO.getEmployeeId(),employeeDTO.getName(),
                employeeDTO.getAddress(),employeeDTO.getPosition(),employeeDTO.getPhoneNumber()));
    }

    @Override
    public ArrayList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException {
        return employeeDAO.getAllIds();
  }

    @Override
    public EmployeeDTO searchEmployee(String selectedEmployeeId) throws SQLException, ClassNotFoundException {
        Employee employee = employeeDAO.search(selectedEmployeeId);
        EmployeeDTO employeeDTO = new EmployeeDTO(employee.getEmployeeId(),employee.getName(),employee.getAddress(),
                employee.getPosition(),employee.getPhoneNumber());
        return employeeDTO;
    }
}
