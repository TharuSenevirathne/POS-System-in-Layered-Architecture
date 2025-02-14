package com.example.lihini_electrical.controller;

import com.example.lihini_electrical.bo.BOFactory;
import com.example.lihini_electrical.bo.custom.DepartmentBO;
import com.example.lihini_electrical.bo.custom.EmployeeBO;
import com.example.lihini_electrical.dto.DepartmentDTO;
import com.example.lihini_electrical.dto.EmployeeDTO;
import com.example.lihini_electrical.tdm.DepartmentTM;
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

public class Department implements Initializable {

    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private ImageView LogoImageview;

    @FXML
    private ImageView MainImageview;

    @FXML
    private Label DepartmentidLabel;

    @FXML
    private Label depid;

    @FXML
    private Label Employeename;

    @FXML
    private Label EmployeenameLabel;

    @FXML
    private Label CustomeridLabel;

    @FXML
    private Label EmployeeidLabel;

    @FXML
    private Label NameLabel;

    @FXML
    private Label TitleLabel;

    @FXML
    private Label customerid;

    @FXML
    private TextField NameTextfield;

    @FXML
    private Button ResetButton;

    @FXML
    private Button SaveButton;

    @FXML
    private Button GoToDashboardButton;

    @FXML
    private Button UpdateButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private ComboBox<String > empidComboBox;

    @FXML
    private TableView<DepartmentTM> DepartmentTable;

    @FXML
    private TableColumn<DepartmentTM, String> depidColumn;

    @FXML
    private TableColumn<DepartmentTM, String> empidColumn;

    @FXML
    private TableColumn<DepartmentTM, String> nameColumn;

    DepartmentBO departmentBO = (DepartmentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DEPARTMENT);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        depidColumn.setCellValueFactory(new PropertyValueFactory<>("departmentid"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        empidColumn.setCellValueFactory(new PropertyValueFactory<>("employeeid"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Department id.").show();
        }
    }

    private void refreshPage() throws SQLException,ClassNotFoundException {
        loadNextDepartmentId();
        loadTableData();
        loadEmployeeIds();

        SaveButton.setDisable(false);
        UpdateButton.setDisable(true);
        DeleteButton.setDisable(true);

        NameTextfield.setText(" ");
        empidComboBox.getSelectionModel().clearSelection();
    }

    private void loadEmployeeIds() throws SQLException,ClassNotFoundException {
        ArrayList<String> employeeIds = employeeBO.getAllEmployeeIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(employeeIds);
        empidComboBox.setItems(observableList);
    }

    private void loadTableData() throws SQLException,ClassNotFoundException {
        ArrayList<DepartmentDTO> departmentDTOS = departmentBO.getAllDepartments();

        ObservableList<DepartmentTM> departmentTMS = FXCollections.observableArrayList();

        for (DepartmentDTO departmentDTO : departmentDTOS) {
            DepartmentTM departmentTM = new DepartmentTM(
                    departmentDTO.getDepartmentid(),
                    departmentDTO.getName(),
                    departmentDTO.getEmployeeid());

            departmentTMS.add(departmentTM);
        }
        DepartmentTable.setItems(departmentTMS);
    }

    private void loadNextDepartmentId() throws SQLException , ClassNotFoundException{
        String nextDepartmentId = departmentBO.generateDeliveryID();
        depid.setText(nextDepartmentId);
    }

    @FXML
    void DeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String departmentId = depid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure , you want to delete this Department?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = departmentBO.deleteDepartment(departmentId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Department deleted....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete department.....!!").show();
            }
        }
    }

    @FXML
    void DepartmentOnMouseClicked(MouseEvent event) {
        DepartmentTM departmentTM = DepartmentTable.getSelectionModel().getSelectedItem();

        if (departmentTM != null) {
            depid.setText(departmentTM.getDepartmentid());
            NameTextfield.setText(departmentTM.getName());
            empidComboBox.getId();

            SaveButton.setDisable(true);
            DeleteButton.setDisable(false);
            UpdateButton.setDisable(false);

        }
    }

    @FXML
    void EmployeeidOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedEmployeeId = empidComboBox.getSelectionModel().getSelectedItem();
        EmployeeDTO employeeDTO = employeeBO.searchEmployee(selectedEmployeeId);

        if (employeeDTO != null) {

            Employeename.setText(employeeDTO.getName());

        }
    }

    @FXML
    void GoToDashboardOnAction(ActionEvent event) throws IOException {
    AnchorPane load = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
    MainAnchorpane.getChildren().clear();
    MainAnchorpane.getChildren().add(load);
    }

    @FXML
    void REsetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
    refreshPage();
    }

    @FXML
    void SaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String departmentId = depid.getText();
        String name = NameTextfield.getText();
        String employeeId = empidComboBox.getId();

        NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";

        boolean isValidName = name.matches(namePattern);

        if (!isValidName) {
            System.out.println(NameTextfield.getStyle());
            NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name..!!");
        }

        if (isValidName) {
            DepartmentDTO departmentDTO = new DepartmentDTO(
                    departmentId,
                    name,
                    employeeId
            );

            boolean isSaved = departmentBO.saveDepartment(departmentDTO);

            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Department saved....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save department....!!").show();
            }
        }
    }

    @FXML
    void UpdateOnAction(ActionEvent event) throws SQLException,ClassNotFoundException {
        String departmentId = depid.getText();
        String name = NameTextfield.getText();
        String employeeId = empidComboBox.getId();

        NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";

        boolean isValidName = name.matches(namePattern);

        if (!isValidName) {
            System.out.println(NameTextfield.getStyle());
            NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name..!!");
        }

        if (isValidName) {
            DepartmentDTO departmentDTO = new DepartmentDTO(
                    departmentId,
                    name,
                    employeeId
            );

            boolean isUpdated = departmentBO.updateDepartment(departmentDTO);

            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Department updated....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update department....!!").show();
            }
        }
    }
}
