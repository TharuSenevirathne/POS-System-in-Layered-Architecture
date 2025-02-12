package com.example.lihini_electrical.dao.custom;

import com.example.lihini_electrical.dao.CrudDAO;
import com.example.lihini_electrical.dto.ProductDTO;
import com.example.lihini_electrical.entity.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDAO extends CrudDAO<Product> {
    ArrayList<Product> getAllProducts() throws SQLException, ClassNotFoundException;
    String getNextProductId() throws SQLException, ClassNotFoundException;
    boolean delete(String productId) throws SQLException, ClassNotFoundException;
    boolean save(Product product) throws SQLException, ClassNotFoundException;
    boolean update(Product product) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllProductIds() throws SQLException, ClassNotFoundException;
    ProductDTO findById(String selectedProductId) throws SQLException, ClassNotFoundException;
}
