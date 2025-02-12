package com.example.lihini_electrical.controller;

import com.example.lihini_electrical.bo.BOFactory;
import com.example.lihini_electrical.bo.custom.SupplierBO;
import com.example.lihini_electrical.dto.SupplierDTO;
import com.example.lihini_electrical.tdm.SupplierTM;
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

public class Supplier implements Initializable {

    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private ImageView MainImageview;

    @FXML
    private ImageView LogoImageview;

    @FXML
    private Label BrandLabel;

    @FXML
    private Label supplierid;

    @FXML
    private Label NameLabel;

    @FXML
    private Label PhoneNoLabel;

    @FXML
    private Label SupplieridLabel;

    @FXML
    private Label TitleLabel;

    @FXML
    private TextField BrandTextfield;

    @FXML
    private TextField NameTextfield;

    @FXML
    private TextField PhoneNoTextfield;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button GoToDashboardButton;

    @FXML
    private Button ResetButton;

    @FXML
    private Button SaveButton;

    @FXML
    private Button UpdateButton;

    @FXML
    private TableView<SupplierTM> SupplierTable;

    @FXML
    private TableColumn<SupplierTM, String > brandColumn;

    @FXML
    private TableColumn<SupplierTM, String > nameColumn;

    @FXML
    private TableColumn<SupplierTM, String > phoneNoColumn;

    @FXML
    private TableColumn<SupplierTM, String > supidColumn;

    SupplierBO supplierBO =(SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        supidColumn.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        phoneNoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Supplier id.").show();
        }
    }

    private void refreshPage() throws SQLException,ClassNotFoundException {
        loadNextSupplierId();
        loadTableData();

        SaveButton.setDisable(false);
        UpdateButton.setDisable(true);
        DeleteButton.setDisable(true);

        NameTextfield.setText("");
        BrandTextfield.setText("");
        PhoneNoTextfield.setText("");
    }

    private void loadTableData() throws SQLException,ClassNotFoundException {
        ArrayList<SupplierDTO> supplierDTOS = supplierBO.getAllSuppliers();

        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();

        for (SupplierDTO supplierDTO : supplierDTOS) {
            SupplierTM supplierTM = new SupplierTM(
                    supplierDTO.getSupplierId(),
                    supplierDTO.getName(),
                    supplierDTO.getBrand(),
                    supplierDTO.getPhoneNo()
            );
            supplierTMS.add(supplierTM);
        }
        SupplierTable.setItems(supplierTMS);
    }

    private void loadNextSupplierId() throws SQLException ,ClassNotFoundException{
        String nextSupplierId = supplierBO.getNextSupplierId();
        supplierid.setText(nextSupplierId);
    }

    @FXML
    void DeleteOnActtion(ActionEvent event) throws SQLException,ClassNotFoundException {
        String supplierId = supplierid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure , you want to delete this Supplier?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = supplierBO.deleteSupplier(supplierId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Supplier deleted....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete supplier.....!!").show();
            }
        }
    }

    @FXML
    void GoToDashboardOnAction(ActionEvent event) throws IOException {
    AnchorPane load = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
    MainAnchorpane.getChildren().clear();
    MainAnchorpane.getChildren().add(load);
    }

    @FXML
    void ResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
    refreshPage();
    }

    @FXML
    void SaveOnAction(ActionEvent event) throws SQLException,ClassNotFoundException {
        String supplierId = supplierid.getText();
        String name = NameTextfield.getText();
        String brand = BrandTextfield.getText();
        String phoneNo = PhoneNoTextfield.getText();

        NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        BrandTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        PhoneNoTextfield.setStyle(PhoneNoTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String brandPattern = "^[A-Za-z ]+$";
        String phoneNoPattern = "^[0-9]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidBrand = brand.matches(brandPattern);
        boolean isValidPhoneNo = phoneNo.matches(phoneNoPattern);

        if (!isValidName) {
            System.out.println(NameTextfield.getStyle());
            NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name..!!");
        }

        if (!isValidBrand) {
            System.out.println(BrandTextfield.getStyle());
            BrandTextfield.setStyle(BrandTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid brand..!!");
        }

        if (!isValidPhoneNo) {
            System.out.println(PhoneNoTextfield.getStyle());
            PhoneNoTextfield.setStyle(PhoneNoTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid phone no..!!");
        }

        if (isValidName && isValidBrand && isValidPhoneNo) {
            SupplierDTO supplierDTO = new SupplierDTO(
                    supplierId,
                    name,
                    brand,
                    phoneNo
            );

            boolean isSaved = supplierBO.saveSupplier(supplierDTO);

            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Supplier saved....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save supplier....!!").show();
            }
        }
    }

    @FXML
    void SupplierOnMouseClicked(MouseEvent event) {
        SupplierTM supplierTM = SupplierTable.getSelectionModel().getSelectedItem();

        if (supplierTM != null) {
            supplierid.setText(supplierTM.getSupplierId());
            NameTextfield.setText(supplierTM.getName());
            BrandTextfield.setText(supplierTM.getBrand());
            PhoneNoTextfield.setText(supplierTM.getPhoneNo());

            SaveButton.setDisable(true);
            DeleteButton.setDisable(false);
            UpdateButton.setDisable(false);
        }
    }

    @FXML
    void UpdateOnAction(ActionEvent event) throws SQLException,ClassNotFoundException {
        String supplierId = supplierid.getText();
        String name = NameTextfield.getText();
        String brand = BrandTextfield.getText();
        String phoneNo = PhoneNoTextfield.getText();

        NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        BrandTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        PhoneNoTextfield.setStyle(PhoneNoTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String brandPattern = "^[A-Za-z ]+$";
        String phoneNoPattern = "^[0-9]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidBrand = brand.matches(brandPattern);
        boolean isValidPhoneNo = phoneNo.matches(phoneNoPattern);

        if (!isValidName) {
            System.out.println(NameTextfield.getStyle());
            NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name..!!");
        }

        if (!isValidBrand) {
            System.out.println(BrandTextfield.getStyle());
            BrandTextfield.setStyle(BrandTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid brand..!!");
        }

        if (!isValidPhoneNo) {
            System.out.println(PhoneNoTextfield.getStyle());
            PhoneNoTextfield.setStyle(PhoneNoTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid phone no..!!");
        }

        if (isValidName && isValidBrand && isValidPhoneNo) {
            SupplierDTO supplierDTO = new SupplierDTO(
                    supplierId,
                    name,
                    brand,
                    phoneNo
            );

            boolean isUpdated = supplierBO.updateSupplier(supplierDTO);

            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Supplier updated....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update supplier....!!").show();
            }
        }
    }
}
