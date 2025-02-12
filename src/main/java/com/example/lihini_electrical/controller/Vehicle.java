package com.example.lihini_electrical.controller;

import com.example.lihini_electrical.bo.BOFactory;
import com.example.lihini_electrical.bo.custom.VehicleBO;
import com.example.lihini_electrical.tdm.VehicleTM;
import com.example.lihini_electrical.dto.VehicleDTO;
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

public class Vehicle implements Initializable {
    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private ImageView MainImageview;

    @FXML
    private ImageView LogoImageview;

    @FXML
    private Label VehicleidLabel;

    @FXML
    private Label Vehiid;

    @FXML
    private Label TypeLabel;

    @FXML
    private Label VehicleLabel;

    @FXML
    private TextField TypeTextfield;

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
    private TableView<VehicleTM> VehicleTable;

    @FXML
    private TableColumn<VehicleTM, String> typeColumn;

    @FXML
    private TableColumn<VehicleTM, String > vehidColumn;

    VehicleBO vehicleBO = (VehicleBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.VEHICLE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    vehidColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Vehicle id.").show();
        }
    }

    private void refreshPage() throws SQLException ,ClassNotFoundException{
        loadNextVehicleId();
        loadTableData();

        SaveButton.setDisable(false);
        UpdateButton.setDisable(true);
        DeleteButton.setDisable(true);

        TypeTextfield.setText("");
    }

    private void loadTableData() throws SQLException,ClassNotFoundException {
        ArrayList<VehicleDTO> vehicleDTOS = vehicleBO.getAllVehicles();

        ObservableList<VehicleTM> vehicleTMS = FXCollections.observableArrayList();

        for (VehicleDTO vehicleDTO : vehicleDTOS) {
            VehicleTM vehicleTM = new VehicleTM(
                    vehicleDTO.getVehicleId(),
                    vehicleDTO.getType()
            );
            vehicleTMS.add(vehicleTM);
        }
        VehicleTable.setItems(vehicleTMS);
    }

    private void loadNextVehicleId() throws SQLException ,ClassNotFoundException{
        String nextVehicleId = vehicleBO.generateVehicleId();
        Vehiid.setText(nextVehicleId);
    }

    @FXML
    void VehicleOnMouseClicked(MouseEvent event) {
        VehicleTM vehicleTM = VehicleTable.getSelectionModel().getSelectedItem();

        if (vehicleTM != null) {
            Vehiid.setText(vehicleTM.getVehicleId());
            TypeTextfield.setText(vehicleTM.getType());

            SaveButton.setDisable(true);
            DeleteButton.setDisable(false);
            UpdateButton.setDisable(false);
        }
    }

    @FXML
    void SaveOnActionn(ActionEvent event) throws SQLException ,ClassNotFoundException{
        String vehicleId = Vehiid.getText();
        String type = TypeTextfield.getText();

        TypeTextfield.setStyle(TypeTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String typePattern = "^[a-zA-Z]+$";

        boolean isValidType = type.matches(typePattern);

        if (!isValidType) {
            System.out.println(TypeTextfield.getStyle());
            TypeTextfield.setStyle(TypeTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid type...!!");
        }

        if (isValidType) {
            VehicleDTO vehicleDTO = new VehicleDTO(
                    vehicleId,
                    type
            );

            boolean isSaved = vehicleBO.saveVehicle(vehicleDTO);

            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Vehicle saved....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save vehicle....!!").show();
            }
        }
    }

    @FXML
    void DeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String vehicleId = Vehiid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure , you want to delete this Vehicle?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = vehicleBO.deleteVehicle(vehicleId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Vehicle deleted....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete vehicle.....!!").show();
            }
        }
    }

    @FXML
    void UpdateOnAction(ActionEvent event) throws SQLException,ClassNotFoundException {
        String vehicleId = Vehiid.getText();
        String type = TypeTextfield.getText();

        TypeTextfield.setStyle(TypeTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String typePattern = "^[a-zA-Z]+$";

        boolean isValidType = type.matches(typePattern);

        if (!isValidType) {
            System.out.println(TypeTextfield.getStyle());
            TypeTextfield.setStyle(TypeTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid type...!!");
        }

        if (isValidType) {
            VehicleDTO vehicleDTO = new VehicleDTO(
                    vehicleId,
                    type
            );

            boolean isUpdated = vehicleBO.updateVehicle(vehicleDTO);

            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Vehicle updated....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update vehicle....!!").show();
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
}
