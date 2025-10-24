package com.example.lihini_electrical.bo.custom.impl;

import com.example.lihini_electrical.bo.custom.CustomerBO;
import com.example.lihini_electrical.dao.DAOFactory;
import com.example.lihini_electrical.dao.custom.CustomerDAO;
import com.example.lihini_electrical.dto.CustomerDTO;
import com.example.lihini_electrical.entity.Customer;
import com.example.lihini_electrical.tdm.CustomerTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.SQLException;
import java.util.ArrayList;

public class  CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = customerDAO.getAll();
        ArrayList<CustomerDTO> customerDTOs = new ArrayList<>();
        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();
        for (Customer customer1 : customers) {
            customerDTOs.add(new CustomerDTO(customer1.getCustomerId(),
                    customer1.getName(), customer1.getAddress(), customer1.getPhoneNo(),
                    customer1.getEmail(), customer1.getType(), customer1.getEmployeeId()));
        }
        return customerDTOs;
    }

    @Override
    public String generateCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.generateId();
    }

    @Override
    public boolean saveCustomer(CustomerDTO dto ) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(dto.getCustomerId(),dto.getName(),
                dto.getAddress(),dto.getPhoneNo(),dto.getEmail(),
                dto.getType(),dto.getEmployeeId()));
    }

    @Override
    public boolean deleteCustomer(String customerId) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(customerId);
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getCustomerId(),dto.getName(),dto.getAddress(),
                dto.getPhoneNo(),dto.getEmail(),dto.getType(),dto.getEmployeeId()));
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return customerDAO.getAllIds();
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(id);
        CustomerDTO customerDTO = new CustomerDTO(customer.getCustomerId(),customer.getName(),customer.getAddress(),
                customer.getPhoneNo(),customer.getEmail(),customer.getType(),customer.getEmployeeId());
                return customerDTO;
    }

}
