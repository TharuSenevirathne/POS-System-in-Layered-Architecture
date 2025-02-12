package com.example.lihini_electrical.bo.custom.impl;

import com.example.lihini_electrical.bo.custom.WarehouseBO;
import com.example.lihini_electrical.dao.DAOFactory;
import com.example.lihini_electrical.dao.custom.SupplierDAO;
import com.example.lihini_electrical.dao.custom.WarehouseDAO;
import com.example.lihini_electrical.dto.SupplierDTO;
import com.example.lihini_electrical.dto.WarehouseDTO;
import com.example.lihini_electrical.entity.Supplier;
import com.example.lihini_electrical.entity.Warehouse;
import com.example.lihini_electrical.tdm.SupplierTM;
import com.example.lihini_electrical.tdm.WarehouseTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class WarehouseBOImpl implements WarehouseBO {
    WarehouseDAO warehouseDAO = (WarehouseDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.WAREHOUSE);
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    @Override
    public ArrayList<WarehouseDTO> getAllWarehouses() throws SQLException, ClassNotFoundException {
        ArrayList<WarehouseDTO> allWarehouses = new ArrayList<>();
        ArrayList<Warehouse> warehouseDTOS = warehouseDAO.getAll();

        ObservableList<WarehouseTM> warehouseTMS = FXCollections.observableArrayList();

        for (Warehouse warehouseDTO : warehouseDTOS) {
             allWarehouses.add(new WarehouseDTO(warehouseDTO.getWarehouseId(),
                     warehouseDTO.getName(),
                     warehouseDTO.getLocation()));
        }
    return allWarehouses;
    }

    @Override
    public String generateWarehouseId() throws SQLException, ClassNotFoundException {
        String nextWarehouseId = warehouseDAO.generateId();
        return nextWarehouseId;
    }

    @Override
    public boolean deleteWarehouse(String warehouseId) throws SQLException, ClassNotFoundException {
        return warehouseDAO.delete(warehouseId);
    }

    @Override
    public boolean saveWarehouse(WarehouseDTO warehouseDTO) throws SQLException, ClassNotFoundException {
        return warehouseDAO.save(new Warehouse(warehouseDTO.getWarehouseId(),warehouseDTO.getName(),
                warehouseDTO.getLocation()));
    }

    @Override
    public boolean updateWarehouse(WarehouseDTO warehouseDTO) throws SQLException, ClassNotFoundException {
        return warehouseDAO.update(new Warehouse(warehouseDTO.getWarehouseId(),warehouseDTO.getName(),
                warehouseDTO.getLocation()));    }

    @Override
    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDTO> allSuppliers = new ArrayList<>();
        ArrayList<Supplier> supplierDTOS = supplierDAO.getAll();

        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();

        for (Supplier supplierDTO : supplierDTOS) {
             allSuppliers.add(new SupplierDTO(supplierDTO.getSupplierId(),
                     supplierDTO.getName(),
                     supplierDTO.getBrand(),
                     supplierDTO.getPhoneNo()));
        }
        return allSuppliers;
    }

    @Override
    public String generateSupplierId() throws SQLException, ClassNotFoundException {
        String nextSupplierId = supplierDAO.generateId();
        return nextSupplierId;
    }

    @Override
    public boolean deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException {
        return warehouseDAO.delete(supplierId);
    }

    @Override
    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(new Supplier(supplierDTO.getSupplierId(),supplierDTO.getName(),
                supplierDTO.getBrand(),supplierDTO.getPhoneNo()));
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(supplierDTO.getSupplierId(),supplierDTO.getName(),
                supplierDTO.getBrand(),supplierDTO.getPhoneNo()));    }
}
