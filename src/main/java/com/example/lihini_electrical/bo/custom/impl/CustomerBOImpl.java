package com.example.lihini_electrical.bo.custom.impl;

import com.example.lihini_electrical.bo.custom.CustomerBO;
import com.example.lihini_electrical.dao.DAOFactory;
import com.example.lihini_electrical.dao.custom.CustomerDAO;
import com.example.lihini_electrical.dto.CustomerDTO;
import com.example.lihini_electrical.entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class  CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO)
            DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> customerDTOArrayList = new ArrayList<>();
        ArrayList<Customer> customer = customerDAO.getAllCustomers();
        for (Customer customer1 : customer) {
            customerDTOArrayList.add(new CustomerDTO(customer1.getCustomerId(),
                    customer1.getName(), customer1.getAddress(), customer1.getPhoneNo(),
                    customer1.getEmail(), customer1.getType(), customer1.getEmployeeId()));
        }
        return customerDTOArrayList;
    }

    @Override
    public String getNextCustomerId() throws SQLException, ClassNotFoundException {
        String nextCustomerId = customerDAO.getNextCustomerId();
        return nextCustomerId;
    }

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(customerDTO.getCustomerId(),customerDTO.getName(),
                customerDTO.getAddress(),customerDTO.getPhoneNo(),customerDTO.getEmail(),
                customerDTO.getType(),customerDTO.getEmployeeId()));
    }

    @Override
    public boolean deleteCustomer(String customerId) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(customerId);
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(customerDTO.getCustomerId(),customerDTO.getName(),customerDTO.getAddress(),
                customerDTO.getPhoneNo(),customerDTO.getEmail(),customerDTO.getType(),customerDTO.getEmployeeId()));
    }

    @Override
    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> customerids = new ArrayList<>();
        ArrayList<String> customerIds = customerDAO.getAllCustomerIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerIds);
        CustomeridCombobox.setItems(observableList);
        return null;
    }


    @Override
    public CustomerDTO findById(String selectedCustomerId) throws SQLException, ClassNotFoundException {
        String selectedCustomerid = CustomeridCombobox.getSelectionModel().getSelectedItem();
        CustomerDTO customerDTO = customerDAO.findById(selectedCustomerId);

        if (customerDTO != null) {
            CustomerName.setText(customerDTO.getName());
        }
        return customerDTO;
    }

}
