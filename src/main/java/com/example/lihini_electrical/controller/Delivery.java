package com.example.lihini_electrical.controller;

import com.example.lihini_electrical.bo.BOFactory;
import com.example.lihini_electrical.bo.custom.DeliveryBO;
import com.example.lihini_electrical.bo.custom.VehicleBO;
import com.example.lihini_electrical.dto.DeliveryDTO;
import com.example.lihini_electrical.tdm.DeliveryTM;
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
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class Delivery implements Initializable {
    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private ImageView MainImageview;

    @FXML
    private ImageView LogoImageView;

    @FXML
    private Label AddressLabel;

    @FXML
    private Label DateLabel;

    @FXML
    private Label Deliid;

    @FXML
    private Label VehicleidLabel;

    @FXML
    private Label DeliveryidLabel;

    @FXML
    private Label TitleLabel;

    @FXML
    public DatePicker DateTextfield;

    @FXML
    private TextField AddressTextfield;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button GoToDashboardButton;

    @FXML
    private Button ResetButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button updateButton;

    @FXML
    private ComboBox<String> VehicleidCombobox;

    @FXML
    private TableView<DeliveryTM> DeliveryTable;

    @FXML
    private TableColumn<DeliveryTM, String> addressColumn;

    @FXML
    private TableColumn<DeliveryTM, Date> dateColumn;

    @FXML
    private TableColumn<DeliveryTM, String> deliidColumn;

    @FXML
    private TableColumn<DeliveryTM, String> vahicleidColumn;

    DeliveryBO deliveryBO = (DeliveryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DELIVERY);
    VehicleBO vehicleBO = (VehicleBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.VEHICLE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deliidColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        vahicleidColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Delivery id.").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextDeliveryId();
        loadTableData();
        loadVehicleIds();

        saveButton.setDisable(false);
        updateButton.setDisable(true);
        DeleteButton.setDisable(true);

        AddressTextfield.setText("");
        dateColumn.setText("");
        VehicleidCombobox.getSelectionModel().clearSelection();

    }

    private void loadVehicleIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> vehicleIds = vehicleBO.getAllVehicleIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(vehicleIds);
        VehicleidCombobox.setItems(observableList);
    }

    private void loadNextDeliveryId() throws SQLException, ClassNotFoundException {
        String nextDeliveryId = deliveryBO.getNextDeliveryId();
        Deliid.setText(nextDeliveryId);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<DeliveryDTO> deliveryDTOS = deliveryBO.getAllDeliveries();

        ObservableList<DeliveryTM> deliveryTMS = FXCollections.observableArrayList();

        for (DeliveryDTO deliveryDTO : deliveryDTOS) {
            DeliveryTM deliveryTM = new DeliveryTM(
                    deliveryDTO.getDeliveryId(),
                    deliveryDTO.getAddress(),
                    deliveryDTO.getDate(),
                    deliveryDTO.getVehicleId()
            );
            deliveryTMS.add(deliveryTM);
        }
        DeliveryTable.setItems(deliveryTMS);
    }

    @FXML
    void DeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String deliveryId = Deliid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure , you want to delete this Delivery?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = deliveryBO.deleteDelivery(deliveryId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Delivery deleted....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete delivery.....!!").show();
            }
        }
    }

    @FXML
    void DeliveryOnMouseClicked(MouseEvent event) {
        DeliveryTM deliveryTM = DeliveryTable.getSelectionModel().getSelectedItem();

        if (deliveryTM != null) {
            Deliid.setText(deliveryTM.getDeliveryId());
            AddressTextfield.setText(deliveryTM.getAddress());
            DateTextfield.setValue(deliveryTM.getDate().toLocalDate());
            VehicleidCombobox.getId();

            saveButton.setDisable(true);
            DeleteButton.setDisable(false);
            updateButton.setDisable(false);

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
    void UpdateOnAction(ActionEvent event) throws SQLException ,ClassNotFoundException{
        String deliveryId = Deliid.getText();
        String address = AddressTextfield.getText();
        LocalDate localDate = DateTextfield.getValue();
        String vehicleId = VehicleidCombobox.getValue();

        AddressTextfield.setStyle(AddressTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        DateTextfield.setStyle(DateTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String addressPattern = "^[A-Za-z ]+$";

        boolean isValidAddress = address.matches(addressPattern);

        if (!isValidAddress) {
            System.out.println(AddressTextfield.getStyle());
            AddressTextfield.setStyle(AddressTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid address..!!");
        }

        if (localDate == null) {
            System.out.println(DateTextfield.getStyle());
            DateTextfield.setStyle(DateTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid date...!!");
        }

        Date date = Date.valueOf(localDate);

        if (isValidAddress && localDate != null) {
            DeliveryDTO deliveryDTO = new DeliveryDTO(
                    deliveryId,
                    address,
                    date,
                    vehicleId
            );

            boolean isUpdated = deliveryBO.updateDelivery(deliveryDTO);

            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Delivery updated....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update delivery....!!").show();
            }
        }
    }

    @FXML
    void VehicleidCombobox(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedVehicleId = VehicleidCombobox.getSelectionModel().getSelectedItem();
        VehicleDTO vehicleDTO = vehicleBO.findById(selectedVehicleId);

        if (vehicleDTO != null) {
            System.out.println(" ");
        }
    }

    @FXML
    void saveOnAction(ActionEvent event) throws SQLException ,ClassNotFoundException{
        String deliveryId = Deliid.getText();
        String address = AddressTextfield.getText();
        LocalDate localDate = DateTextfield.getValue();
        String vehicleId = VehicleidCombobox.getValue();

        AddressTextfield.setStyle(AddressTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        DateTextfield.setStyle(DateTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String addressPattern = "^[A-Za-z ]+$";

        boolean isValidAddress = address.matches(addressPattern);

        if (!isValidAddress) {
            System.out.println(AddressTextfield.getStyle());
            AddressTextfield.setStyle(AddressTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid address..!!");
        }

        if (localDate == null) {
            System.out.println(DateTextfield.getStyle());
            DateTextfield.setStyle(DateTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid date...!!");
        }

        Date date = Date.valueOf(localDate);

        if (isValidAddress && localDate != null) {
            DeliveryDTO deliveryDTO = new DeliveryDTO(
                    deliveryId,
                    address,
                    date,
                    vehicleId
            );

            boolean isSaved = deliveryBO.saveDelivery(deliveryDTO);

            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Delivery saved....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save delivery....!!").show();
            }
        }
    }

}
