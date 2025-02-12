package com.example.lihini_electrical.controller;


import com.example.lihini_electrical.bo.BOFactory;
import com.example.lihini_electrical.bo.custom.EmployeeBO;
import com.example.lihini_electrical.dto.EmployeeDTO;
import com.example.lihini_electrical.tdm.EmployeeTM;
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

public class Employee implements Initializable {
    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private ImageView MainImageview;

    @FXML
    private ImageView LogoImageview;

    @FXML
    private Label AddressLabel;

    @FXML
    private Label EmployeeidLabel;

    @FXML
    private Label NameLabel;

    @FXML
    private Label PhoneNoLabel;

    @FXML
    private Label PositionLabel;

    @FXML
    private Label TitleLabel;

    @FXML
    private Label empidLabel;

    @FXML
    private TextField AddressTextfield;

    @FXML
    private TextField PhoneNoTextfield;

    @FXML
    private TextField NameTextfield;

    @FXML
    private TextField PositionTextfield;

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
    private TableView<EmployeeTM> EmployeeTable;

    @FXML
    private TableColumn<EmployeeTM, String> addressColumn;

    @FXML
    private TableColumn<EmployeeTM, String> empidColumn;

    @FXML
    private TableColumn<EmployeeTM, String> nameColumn;

    @FXML
    private TableColumn<EmployeeTM, String> phoneNoColumn;

    @FXML
    private TableColumn<EmployeeTM, String> positionColumn;

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        empidColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        phoneNoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Employee id.").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextEmployeeId();
        loadTableData();

        SaveButton.setDisable(false);
        UpdateButton.setDisable(true);
        DeleteButton.setDisable(true);

        NameTextfield.setText("");
        AddressTextfield.setText("");
        PositionTextfield.setText("");
        PhoneNoTextfield.setText("");
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDTO> employeeDTOS = employeeBO.getAllEmployees();

        ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList();

        for (EmployeeDTO employeeDTO : employeeDTOS) {
            EmployeeTM employeeTM = new EmployeeTM(
                    employeeDTO.getEmployeeId(),
                    employeeDTO.getName(),
                    employeeDTO.getAddress(),
                    employeeDTO.getPosition(),
                    employeeDTO.getPhoneNumber()
            );
            employeeTMS.add(employeeTM);
        }
        EmployeeTable.setItems(employeeTMS);
    }

    private void loadNextEmployeeId() throws SQLException, ClassNotFoundException {
        String nextEmployeeId = employeeBO.generateEmployeeId();
        empidLabel.setText(nextEmployeeId);
    }

    @FXML
    void EmployeeOnMouseClicked(MouseEvent event) {
    EmployeeTM employeeTM = EmployeeTable.getSelectionModel().getSelectedItem();

        if (employeeTM != null) {
            empidLabel.setText(employeeTM.getEmployeeId());
            NameTextfield.setText(employeeTM.getName());
            AddressTextfield.setText(employeeTM.getAddress());
            PositionTextfield.setText(employeeTM.getPosition());
            PhoneNoTextfield.setText(employeeTM.getPhoneNumber());

            SaveButton.setDisable(true);
            DeleteButton.setDisable(false);
            UpdateButton.setDisable(false);
        }
    }

    @FXML
    void SaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String employeeId = empidLabel.getText();
        String name = NameTextfield.getText();
        String address = AddressTextfield.getText();
        String position = PositionTextfield.getText();
        String phoneNumber = PhoneNoTextfield.getText();

        NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        AddressTextfield.setStyle(AddressTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        PositionTextfield.setStyle(PositionTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        PhoneNoTextfield.setStyle(PhoneNoTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String addressPattern = "^[A-Za-z ]+$";
        String positionPattern = "^[A-Za-z ]+$";
        String phoneNoPattern = "^[0-9]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidAddress = address.matches(addressPattern);
        boolean isValidPosition = position.matches(positionPattern);
        boolean isValidPhoneNumber = phoneNumber.matches(phoneNoPattern);

            if (!isValidName){
                System.out.println(NameTextfield.getStyle());
                NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: red;");
                System.out.println("Invalid Name..!!");
            }

            if (!isValidAddress){
                System.out.println(AddressTextfield.getStyle());
                AddressTextfield.setStyle(AddressTextfield.getStyle() + ";-fx-border-color: red;");
                System.out.println("Invalid Address..!!");
            }

            if (!isValidPosition){
                System.out.println(PositionTextfield.getStyle());
                PositionTextfield.setStyle(PositionTextfield.getStyle() + ";-fx-border-color: red;");
                System.out.println("Invalid Position..!!");
            }

            if (!isValidPhoneNumber){
                System.out.println(PhoneNoTextfield.getStyle());
                PhoneNoTextfield.setStyle(PhoneNoTextfield.getStyle() + ";-fx-border-color: red;");
                System.out.println("Invalid Phone Number...!!");
            }

            if (isValidName && isValidAddress && isValidPosition && isValidPhoneNumber){
                EmployeeDTO employeeDTO = new EmployeeDTO(
                        employeeId,
                        name,
                        address,
                        position,
                        phoneNumber
                );

                boolean isSaved = employeeBO.saveEmployee(employeeDTO);

                if (isSaved){
                    refreshPage();
                    new Alert(Alert.AlertType.INFORMATION, "Employee Saved...").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save Employee.....!!").show();
                }
            }
    }

    @FXML
    void DeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
    String employeeId = empidLabel.getText();

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure , you want to delete this Employee ??",ButtonType.YES,ButtonType.NO);
    Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){
            boolean isDeleted = employeeBO.deleteEmployee(employeeId);
            if (isDeleted){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Employee Deleted...").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete Employee.....!!").show();
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
    void UpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String employeeId = empidLabel.getText();
        String name = NameTextfield.getText();
        String address = AddressTextfield.getText();
        String position = PositionTextfield.getText();
        String phoneNumber = PhoneNoTextfield.getText();

        NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        AddressTextfield.setStyle(AddressTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        PositionTextfield.setStyle(PositionTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        PhoneNoTextfield.setStyle(PhoneNoTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String addressPattern = "^[A-Za-z ]+$";
        String positionPattern = "^[A-Za-z ]+$";
        String phoneNoPattern = "^[0-9]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidAddress = address.matches(addressPattern);
        boolean isValidPosition = position.matches(positionPattern);
        boolean isValidPhoneNumber = phoneNumber.matches(phoneNoPattern);

        if (!isValidName){
            System.out.println(NameTextfield.getStyle());
            NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid Name..!!");
        }

        if (!isValidAddress){
            System.out.println(AddressTextfield.getStyle());
            AddressTextfield.setStyle(AddressTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid Address..!!");
        }

        if (!isValidPosition){
            System.out.println(PositionTextfield.getStyle());
            PositionTextfield.setStyle(PositionTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid Position..!!");
        }

        if (!isValidPhoneNumber){
            System.out.println(PhoneNoTextfield.getStyle());
            PhoneNoTextfield.setStyle(PhoneNoTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid Phone Number...!!");
        }

        if (isValidName && isValidAddress && isValidPosition && isValidPhoneNumber){
            EmployeeDTO employeeDTO = new EmployeeDTO(
                    employeeId,
                    name,
                    address,
                    position,
                    phoneNumber
            );

            boolean isUpdate = employeeBO.updateEmployee(employeeDTO);

            if (isUpdate){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Employee Updated...").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Employee.....!!").show();
            }
        }

    }
}

