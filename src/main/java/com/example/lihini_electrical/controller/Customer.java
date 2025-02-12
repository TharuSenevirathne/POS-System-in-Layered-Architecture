package com.example.lihini_electrical.controller;

import com.example.lihini_electrical.bo.BOFactory;
import com.example.lihini_electrical.bo.custom.CustomerBO;
import com.example.lihini_electrical.bo.custom.EmployeeBO;
import com.example.lihini_electrical.db.DBConnection;
import com.example.lihini_electrical.dto.CustomerDTO;
import com.example.lihini_electrical.dto.EmployeeDTO;
import com.example.lihini_electrical.tdm.CustomerTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class Customer implements Initializable {

    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private ImageView MainImageview;

    @FXML
    private ImageView LogoImageview;

    @FXML
    private Label AddressLabel;

    @FXML
    private Label CustomeridLabel;

    @FXML
    private Label Employeeid;

    @FXML
    private Label NameLabel;

    @FXML
    private Label PhoneNoLabel;

    @FXML
    private Label TitleLabel;

    @FXML
    private Label custidLabel;

    @FXML
    private Label TypeLabel;

    @FXML
    private Label EmailLabel;

    @FXML
    private TextField EmailTextfield;

    @FXML
    private TextField AddressTextfield;

    @FXML
    private TextField NameTextfield;

    @FXML
    private TextField TypeTextfield;

    @FXML
    private TextField PhoneNoTextfield;

    @FXML
    private Button DashboardButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button ReportButton;

    @FXML
    private Button ResetButton;

    @FXML
    private Button SaveButton;

    @FXML
    private Button SendMailButton;

    @FXML
    private Button OrderReportButton;

    @FXML
    private Button UpdateButton;

    @FXML
    private ComboBox<String > EmployeeidComboBox;

    @FXML
    private TableView<CustomerTM> CustomerTable;

    @FXML
    private TableColumn<CustomerTM, String> CustomeridColumn;

    @FXML
    private TableColumn<CustomerTM, String> NameColumn;

    @FXML
    private TableColumn<CustomerTM, String> EmployeeidColumn;

    @FXML
    private TableColumn<CustomerTM, String> PhoneNoColumn;

    @FXML
    private TableColumn<CustomerTM, String> TypeColumn;

    @FXML
    private TableColumn<CustomerTM, String> AddressColumn;

    @FXML
    private TableColumn<CustomerTM, String> EmailColumn;

    CustomerBO customerbo = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustomeridColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        PhoneNoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        EmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        EmployeeidColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Customer id.").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextCustomerId();
        loadTableData();
        loadEmployeeIds();

        SaveButton.setDisable(false);
        UpdateButton.setDisable(true);
        DeleteButton.setDisable(true);

        NameTextfield.setText("");
        AddressTextfield.setText("");
        PhoneNoTextfield.setText("");
        EmailTextfield.setText("");
        TypeTextfield.setText("");
        EmployeeidComboBox.getSelectionModel().clearSelection();
    }

    private void loadTableData() {
       CustomerTable.getItems().clear();
        try {
            ArrayList<CustomerDTO> customerDTOS = customerbo.getAllCustomers();
            ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

            for (CustomerDTO customerDTO : customerDTOS) {
                CustomerTable.getItems().add(new CustomerTM(customerDTO.getCustomerId(),
                        customerDTO.getName(),
                        customerDTO.getAddress(),
                        customerDTO.getPhoneNo(),
                        customerDTO.getEmail(),
                        customerDTO.getType(),
                        customerDTO.getEmployeeId()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }catch (ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void loadEmployeeIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> employeeIds = employeeBO.getAllEmployeeIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(employeeIds);
        EmployeeidComboBox.setItems(observableList);
    }

    private void loadNextCustomerId() throws SQLException, ClassNotFoundException {
        String nextCustomerId = customerbo.generateCustomerId();
        custidLabel.setText(nextCustomerId);
    }

    @FXML
    void CustomerTableOnMouseClicked(MouseEvent event) {
        CustomerTM customerTM = CustomerTable.getSelectionModel().getSelectedItem();

        if (customerTM != null) {
            custidLabel.setText(customerTM.getCustomerId());
            NameTextfield.setText(customerTM.getName());
            AddressTextfield.setText(customerTM.getAddress());
            PhoneNoTextfield.setText(customerTM.getPhoneNo());
            EmailTextfield.setText(customerTM.getEmail());
            TypeTextfield.setText(customerTM.getType());
            EmployeeidComboBox.getId();

            SaveButton.setDisable(true);
            DeleteButton.setDisable(false);
            UpdateButton.setDisable(false);

        }
    }

    @FXML
    void SaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String customerId = custidLabel.getText();
        String name = NameTextfield.getText();
        String address = AddressTextfield.getText();
        String phoneNo = PhoneNoTextfield.getText();
        String email = EmailTextfield.getText();
        String type = TypeTextfield.getText();
        String employeeId = EmployeeidComboBox.getValue();
        System.out.println(employeeId);

        NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        AddressTextfield.setStyle(AddressTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        PhoneNoTextfield.setStyle(PhoneNoTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        EmailTextfield.setStyle(EmailTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        TypeTextfield.setStyle(TypeTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String addressPattern = "^[A-Za-z ]+$";
        String phoneNoPattern = "^[0-9]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String typePattern = "^[a-zA-Z]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidAddress = address.matches(addressPattern);
        boolean isValidPhoneNo = phoneNo.matches(phoneNoPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidType = type.matches(typePattern);

        if (!isValidName) {
            System.out.println(NameTextfield.getStyle());
            NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name..!!");
        }

        if (!isValidAddress) {
            System.out.println(AddressTextfield.getStyle());
            AddressTextfield.setStyle(AddressTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid address..!!");
        }

        if (!isValidPhoneNo) {
            System.out.println(PhoneNoTextfield.getStyle());
            PhoneNoTextfield.setStyle(PhoneNoTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid phone no..!!");
        }

        if (!isValidEmail) {
            System.out.println(EmailTextfield.getStyle());
            EmailTextfield.setStyle(EmailTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid email..!!");
        }

        if (!isValidType) {
            System.out.println(TypeTextfield.getStyle());
            TypeTextfield.setStyle(TypeTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid type...!!");
        }

        if (isValidName && isValidAddress && isValidPhoneNo && isValidEmail && isValidType) {
            CustomerDTO customerDTO = new CustomerDTO(
                    customerId,
                    name,
                    address,
                    phoneNo,
                    email,
                    type,
                    employeeId
            );

            boolean isSaved = customerbo.saveCustomer(customerDTO);

            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer saved....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save customer....!!").show();
            }
        }
    }

    @FXML
    void DeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String customerId = custidLabel.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure , you want to delete this Customer?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = customerbo.deleteCustomer(customerId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer deleted....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete customer.....!!").show();
            }
        }
    }

    @FXML
    void UpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String customerId = custidLabel.getText();
        String name = NameTextfield.getText();
        String address = AddressTextfield.getText();
        String phoneNo = PhoneNoTextfield.getText();
        String email = EmailTextfield.getText();
        String type = TypeTextfield.getText();
        String employeeId = EmployeeidComboBox.getValue();

        NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        AddressTextfield.setStyle(AddressTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        PhoneNoTextfield.setStyle(PhoneNoTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        EmailTextfield.setStyle(EmailTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        TypeTextfield.setStyle(TypeTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String addressPattern = "^[A-Za-z ]+$";
        String phoneNoPattern = "^[0-9]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String typePattern = "^[a-zA-Z]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidAddress = address.matches(addressPattern);
        boolean isValidPhoneNo = phoneNo.matches(phoneNoPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidType = type.matches(typePattern);

        if (!isValidName) {
            System.out.println(NameTextfield.getStyle());
            NameTextfield.setStyle(NameTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name..!!");
        }

        if (!isValidAddress) {
            System.out.println(AddressTextfield.getStyle());
            AddressTextfield.setStyle(AddressTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid address..!!");
        }

        if (!isValidPhoneNo) {
            System.out.println(PhoneNoTextfield.getStyle());
            PhoneNoTextfield.setStyle(PhoneNoTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid phone no..!!");
        }

        if (!isValidEmail) {
            System.out.println(EmailTextfield.getStyle());
            EmailTextfield.setStyle(EmailTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid email..!!");
        }

        if (!isValidType) {
            System.out.println(TypeTextfield.getStyle());
            TypeTextfield.setStyle(TypeTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid type...!!");
        }

        if (isValidName && isValidAddress && isValidPhoneNo && isValidEmail && isValidType) {
            CustomerDTO customerDTO = new CustomerDTO(
                    customerId,
                    name,
                    address,
                    phoneNo,
                    email,
                    type,
                    employeeId
            );
            CustomerDTO dto = new CustomerDTO(customerId,name,address,phoneNo,email,type,employeeId);
            boolean isUpdate = customerbo.updateCustomer(dto);

            if (isUpdate) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer updated....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update customer....!!").show();
            }
        }
    }

    @FXML
    void DashboardOnAction(ActionEvent event) throws IOException {
    AnchorPane load = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
    MainAnchorpane.getChildren().clear();
    MainAnchorpane.getChildren().add(load);
    }

    @FXML
    void ResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void EmployeeidOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedEmployeeId = EmployeeidComboBox.getSelectionModel().getSelectedItem();
        EmployeeDTO employeeDTO = employeeBO.searchEmployee(selectedEmployeeId);

        if (employeeDTO != null) {
            System.out.println(" ");
        }
    }

    @FXML
    void ReportOnAction(ActionEvent event) throws ClassNotFoundException {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/report/orderdetails.jrxml"
                            ));

            Connection connection = DBConnection.getDbConnection().getConnection();

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    null,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB error...!").show();
        }
    }

    @FXML
    void SendMailOnAction(ActionEvent event) {
        CustomerTM selectedItem = CustomerTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "Please select customer..!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/SendMail.fxml"));
            Parent load = loader.load();

            SendMail sendMailController = loader.getController();

            String email = selectedItem.getEmail();
            sendMailController.setCustomerEmail(email);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Send email");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/images (1).jpeg")));

            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = UpdateButton.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load UI..!");
            e.printStackTrace();
        }
    }
}


