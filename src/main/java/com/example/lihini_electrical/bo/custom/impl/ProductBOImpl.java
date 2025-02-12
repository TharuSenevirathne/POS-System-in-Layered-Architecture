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
        ArrayList<Product> product = productDAO.getAllProducts();

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
    public String getNextProductId() throws SQLException, ClassNotFoundException {
        String nextProductId = productDAO.getNextProductId();
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
        ArrayList<String> productIds = productDAO.getAllProductIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(productIds);
        ProductidCombobox.setItems(observableList);
        return null;
    }

    @Override
    public ProductDTO findById(String selectedProductId) throws SQLException, ClassNotFoundException {
        String selectedProductId = ProductidCombobox.getSelectionModel().getSelectedItem();
        ProductDTO productDTO = productDAO.findById(selectedProductId);

        // If customer found (customerDTO not null)
        if (productDTO != null) {
            ProductName.setText(productDTO.getName());
            QTYTextfield.setText(productDTO.getQuantity().toString());
            UnitPrice.setText(String.valueOf(productDTO.getPrice()));
        }
        return null;
    }
}
