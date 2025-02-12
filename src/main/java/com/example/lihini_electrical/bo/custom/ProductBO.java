package com.example.lihini_electrical.bo.custom;

import com.example.lihini_electrical.bo.SuperBO;
import com.example.lihini_electrical.dto.ProductDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductBO extends SuperBO {
    ArrayList<ProductDTO> getAllProducts() throws SQLException, ClassNotFoundException;
    String generateProductId() throws SQLException, ClassNotFoundException;
    boolean deleteProduct(String productId) throws SQLException, ClassNotFoundException;
    boolean saveProduct(ProductDTO productDTO) throws SQLException, ClassNotFoundException;
    boolean updateProduct(ProductDTO productDTO) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllProductIds() throws SQLException, ClassNotFoundException;
    ProductDTO search(String selectedProductId) throws SQLException, ClassNotFoundException;
}
