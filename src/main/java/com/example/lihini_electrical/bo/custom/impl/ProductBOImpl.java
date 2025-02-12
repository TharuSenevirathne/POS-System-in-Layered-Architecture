package com.example.lihini_electrical.bo.custom.impl;

import com.example.lihini_electrical.bo.custom.ProductBO;
import com.example.lihini_electrical.dao.DAOFactory;
import com.example.lihini_electrical.dao.custom.ProductDAO;
import com.example.lihini_electrical.dto.ProductDTO;
import com.example.lihini_electrical.entity.Product;
import com.example.lihini_electrical.tdm.ProductTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductBOImpl implements ProductBO {
    ProductDAO productDAO = (ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCT);

    @Override
    public ArrayList<ProductDTO> getAllProducts() throws SQLException, ClassNotFoundException {
        ArrayList<ProductDTO> products = new ArrayList<>();
        ArrayList<Product> product = productDAO.getAll();

        ObservableList<ProductTM> productTMS = FXCollections.observableArrayList();

        for (Product productDTO : product) {
            products.add(new ProductDTO(productDTO.getProductId(),
                    productDTO.getName(),
                    productDTO.getPrice(),
                    productDTO.getQuantity(),
                    productDTO.getInventoryId()));
        }
            return products;
        }

    @Override
    public String generateProductId() throws SQLException, ClassNotFoundException {
        String nextProductId = productDAO.generateId();
        return nextProductId;
    }

    @Override
    public boolean deleteProduct(String productId) throws SQLException, ClassNotFoundException {
        return productDAO.delete(productId);
    }

    @Override
    public boolean saveProduct(ProductDTO productDTO) throws SQLException, ClassNotFoundException {
        return productDAO.save(new Product(productDTO.getProductId(), productDTO.getName(),
                productDTO.getPrice(),productDTO.getQuantity(),productDTO.getInventoryId()));
    }

    @Override
    public boolean updateProduct(ProductDTO productDTO) throws SQLException, ClassNotFoundException {
        return productDAO.update(new Product(productDTO.getProductId(), productDTO.getName(),
                productDTO.getPrice(),productDTO.getQuantity(),productDTO.getInventoryId()));
    }

    @Override
    public ArrayList<String> getAllProductIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> productIds = new ArrayList<>();
        productDAO.getAll();
        return productIds;
    }

    @Override
    public ProductDTO search(String selectedProductId) throws SQLException, ClassNotFoundException {
        Product product = productDAO.search(selectedProductId);
        ProductDTO productDTO = new ProductDTO(product.getProductId(),product.getName(),product.getPrice(),
                product.getInventoryId(),product.getQuantity());
        return productDTO;
    }
}
