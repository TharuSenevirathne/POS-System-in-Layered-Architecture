package com.example.lihini_electrical.controller;

import com.example.lihini_electrical.bo.BOFactory;
import com.example.lihini_electrical.bo.custom.InventoryBO;
import com.example.lihini_electrical.bo.custom.ProductBO;
import com.example.lihini_electrical.dto.ProductDTO;
import com.example.lihini_electrical.tdm.ProductTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class Product implements Initializable {
    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private ImageView MainImageview;

    @FXML
    private ImageView LogoImageview;

    @FXML
    private Label InventoryidLabel;

    @FXML
    private Label TitleLabel;

    @FXML
    private Label proid;

    @FXML
    private Label NameLabel;

    @FXML
    private Label ProductidLabel;

    @FXML
    private Label PriceLabel;

    @FXML
    private TextField NameTextfield;

    @FXML
    private TextField PriceTextfield;

    @FXML
    private Button ResetButton;

    @FXML
    private Label QtyLabel;

    @FXML
    private TextField QtyTextfield;

    @FXML
    private Button SaveButton;

    @FXML
    private Button UpdateButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button GoToDashBoardButton;

    @FXML
    private ComboBox<String> InventoryidCombobox;

    @FXML
    private TableView<ProductTM> ProductTable;

    @FXML
    private TableColumn<ProductTM, String> InventoryidColumn;

    @FXML
    private TableColumn<ProductTM, String> nameColumn;

    @FXML
    private TableColumn<ProductTM, Double > priceColumn;

    @FXML
    private TableColumn<ProductTM, String > productidColumn;

    @FXML
    private TableColumn<ProductTM, String> qtyColumn;

    ProductBO productBO = (ProductBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRODUCT);
    InventoryBO inventoryBO = (InventoryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.INVENTORY);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productidColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        InventoryidColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Product id.").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextProductId();
        loadTableData();
        loadInventoryIds();

        SaveButton.setDisable(false);
        UpdateButton.setDisable(true);
        DeleteButton.setDisable(true);

        NameTextfield.setText(" ");
        PriceTextfield.setText(" ");
        InventoryidCombobox.getSelectionModel().clearSelection();
    }

    private void loadInventoryIds() throws SQLException,ClassNotFoundException {
        String inventoryIds = inventoryBO.getAllInventoryIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(inventoryIds);
        InventoryidCombobox.setItems(observableList);
    }

    private void loadTableData() throws SQLException ,ClassNotFoundException {
        ArrayList<ProductDTO> productDTOS = productBO.getAllProducts();

        ObservableList<ProductTM> productTMS = FXCollections.observableArrayList();

        for (ProductDTO productDTO : productDTOS) {
            ProductTM productTM = new ProductTM(
                    productDTO.getProductId(),
                    productDTO.getName(),
                    productDTO.getPrice(),
                    productDTO.getQuantity(),
                    productDTO.getInventoryId()
            );
            productTMS.add(productTM);
        }
        ProductTable.setItems(productTMS);
    }

    private void loadNextProductId() throws SQLException ,ClassNotFoundException{
        String nextProductId = productBO.generateProductId();
        proid.setText(nextProductId);
    }

    @FXML
    void DeleteOnAction(ActionEvent event) throws SQLException,ClassNotFoundException {
        String productId = proid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure , you want to delete this Product?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = productBO.deleteProduct(productId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Product deleted....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete product.....!!").show();
            }
        }
    }

    @FXML
    void GoToDashBoardOnAction(ActionEvent event) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
        MainAnchorpane.getChildren().clear();
        MainAnchorpane.getChildren().add(load);
    }

    @FXML
    void ProductOnMouseClicked(MouseEvent event) {
        ProductTM productTM = ProductTable.getSelectionModel().getSelectedItem();

        if (productTM != null) {
            proid.setText(productTM.getProductId());
            NameTextfield.setText(productTM.getName());
            PriceTextfield.setText(String.valueOf(productTM.getPrice()));
            InventoryidCombobox.getId();

            SaveButton.setDisable(true);
            DeleteButton.setDisable(false);
            UpdateButton.setDisable(false);
        }
    }

    @FXML
    void ResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
    refreshPage();
    }

    @FXML
    void SaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String productId = proid.getText();
        String name = NameTextfield.getText();
        String priceString = PriceTextfield.getText();
        String quantity = QtyTextfield.getText();
        String inventoryId = InventoryidCombobox.getId();

        NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        PriceTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        QtyTextfield.setStyle(QtyTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String pricePattern = "^\\d{1,3}(,\\d{3})*(\\.\\d{1,2})?$";
        String qtyPattern = "^[0-9]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidPrice = priceString.matches(pricePattern);
        boolean isValidQuantity = quantity.matches(qtyPattern);

        if (!isValidName) {
            System.out.println(NameTextfield.getStyle());
            NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name..!!");
        }

        if (!isValidPrice) {
            System.out.println(PriceTextfield.getStyle());
            PriceTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid price...!!");
        }

        if (!isValidQuantity) {
            System.out.println(QtyTextfield.getStyle());
            QtyTextfield.setStyle(QtyTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid quantity..!!");
        }

        if (isValidName && isValidPrice) {
            ProductDTO productDTO = new ProductDTO(
                    productId,
                    name,
                    Double.parseDouble(priceString) ,
                    quantity,
                    inventoryId
            );

            boolean isSaved = productBO.saveProduct(productDTO);

            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Product saved....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save product....!!").show();
            }
        }
    }

    @FXML
    void UpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String productId = proid.getText();
        String name = NameTextfield.getText();
        String priceString = PriceTextfield.getText();
        String quantity = QtyTextfield.getText();
        String inventoryId = InventoryidCombobox.getId();

        NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        PriceTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        QtyTextfield.setStyle(QtyTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String pricePattern = "^\\d{1,3}(,\\d{3})*(\\.\\d{1,2})?$";
        String qtyPattern = "^[0-9]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidPrice = priceString.matches(pricePattern);
        boolean isValidQuantity = quantity.matches(qtyPattern);

        if (!isValidName) {
            System.out.println(NameTextfield.getStyle());
            NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name..!!");
        }

        if (!isValidPrice) {
            System.out.println(PriceTextfield.getStyle());
            PriceTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid price...!!");
        }

        if (!isValidQuantity) {
            System.out.println(QtyTextfield.getStyle());
            QtyTextfield.setStyle(QtyTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid quantity..!!");
        }

        if (isValidName && isValidPrice) {
            ProductDTO productDTO = new ProductDTO(
                    productId,
                    name,
                    Double.parseDouble(priceString),
                    quantity,
                    inventoryId
            );

            boolean isUpdated = productBO.updateProduct(productDTO);

            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Product updated....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update product....!!").show();
            }
        }
    }
}
