package com.example.lihini_electrical.controller;

import com.example.lihini_electrical.bo.BOFactory;
import com.example.lihini_electrical.bo.custom.WarehouseBO;
import com.example.lihini_electrical.tdm.WarehouseTM;
import com.example.lihini_electrical.dto.WarehouseDTO;
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

public class Warehouse implements Initializable {
    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private ImageView MainImageview;

    @FXML
    private ImageView LogoImageview;

    @FXML
    private Label NameLabel;

    @FXML
    private Label LocationLabel;

    @FXML
    private Label warehouseid;

    @FXML
    private Label WarehouseidLabel;

    @FXML
    private Label TitleLabel;

    @FXML
    private TextField LocationTextfield;

    @FXML
    private TextField NameTextfield;

    @FXML
    private Button ResetButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button GoToDashboardButton;

    @FXML
    private Button SaveButton;

    @FXML
    private Button UpdateButton;

    @FXML
    private TableView<WarehouseTM> WarehouseTable;

    @FXML
    private TableColumn<WarehouseTM, String> nameColumn;

    @FXML
    private TableColumn<WarehouseTM, String> LocationColumn;

    @FXML
    private TableColumn<WarehouseTM, String> warehouseidColumn;

    WarehouseBO warehouseBO = (WarehouseBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.WAREHOUSE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        warehouseidColumn.setCellValueFactory(new PropertyValueFactory<>("warehouseId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        LocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Warehouse id.").show();
        }
    }

    private void refreshPage() throws SQLException,ClassNotFoundException {
        loadNextWarehouseId();
        loadTableData();

        SaveButton.setDisable(false);
        UpdateButton.setDisable(true);
        DeleteButton.setDisable(true);

        NameTextfield.setText("");
        LocationTextfield.setText("");
    }

    private void loadTableData() throws SQLException,ClassNotFoundException {
        ArrayList<WarehouseDTO> warehouseDTOS = warehouseBO.getAllWarehouses();

        ObservableList<WarehouseTM> warehouseTMS = FXCollections.observableArrayList();

        for (WarehouseDTO warehouseDTO : warehouseDTOS) {
            WarehouseTM warehouseTM = new WarehouseTM(
                    warehouseDTO.getWarehouseId(),
                    warehouseDTO.getName(),
                    warehouseDTO.getLocation()
            );
            warehouseTMS.add(warehouseTM);
        }
        WarehouseTable.setItems(warehouseTMS);
    }

    private void loadNextWarehouseId() throws SQLException ,ClassNotFoundException{
        String nextWarehouseId = warehouseBO.getNextWarehouseId();
        warehouseid.setText(nextWarehouseId);
    }

    @FXML
    void DeleteOnAction(ActionEvent event) throws SQLException,ClassNotFoundException {
        String warehouseId = warehouseid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure , you want to delete this Warehouse?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = warehouseBO.deleteWarehouse(warehouseId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Warehouse deleted....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete warehouse.....!!").show();
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
        String warehouseId = warehouseid.getText();
        String name = NameTextfield.getText();
        String location = LocationTextfield.getText();


        NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        LocationTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String locationPattern = "^[A-Za-z ]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidLocation = location.matches(locationPattern);

        if (!isValidName) {
            System.out.println(NameTextfield.getStyle());
            NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name..!!");
        }

       if (!isValidLocation) {
           System.out.println(LocationTextfield.getStyle());
           LocationTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: red;");
           System.out.println("Invalid location..!!");
       }

        if (isValidName && isValidLocation) {
            WarehouseDTO warehouseDTO = new WarehouseDTO(
                    warehouseId,
                    name,
                    location
            );

            boolean isSaved = warehouseBO.saveWarehouse(warehouseDTO);

            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Warehouse saved....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save warehouse....!!").show();
            }
        }
    }

    @FXML
    void UpdateOnAction(ActionEvent event) throws SQLException,ClassNotFoundException {
        String warehouseId = warehouseid.getText();
        String name = NameTextfield.getText();
        String location = LocationTextfield.getText();


        NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        LocationTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String locationPattern = "^[A-Za-z ]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidLocation = location.matches(locationPattern);

        if (!isValidName) {
            System.out.println(NameTextfield.getStyle());
            NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name..!!");
        }

        if (!isValidLocation) {
            System.out.println(LocationTextfield.getStyle());
            LocationTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid location..!!");
        }

        if (isValidName && isValidLocation) {
            WarehouseDTO warehouseDTO = new WarehouseDTO(
                    warehouseId,
                    name,
                    location
            );

            boolean isUpdated = warehouseBO.updateWarehouse(warehouseDTO);

            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Warehouse updated....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update warehouse....!!").show();
            }
        }
    }

    @FXML
    void WarehouseOnMouseClicked(MouseEvent event) {
        WarehouseTM warehouseTM = WarehouseTable.getSelectionModel().getSelectedItem();

        if (warehouseTM != null) {
            warehouseid.setText(warehouseTM.getWarehouseId());
            NameTextfield.setText(warehouseTM.getName());
            LocationTextfield.setText(warehouseTM.getLocation());

            SaveButton.setDisable(true);
            DeleteButton.setDisable(false);
            UpdateButton.setDisable(false);
        }
    }
}