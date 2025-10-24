package com.example.lihini_electrical.controller;

import com.example.lihini_electrical.bo.BOFactory;
import com.example.lihini_electrical.bo.custom.PaymentBO;
import com.example.lihini_electrical.dto.PaymentDTO;
import com.example.lihini_electrical.tdm.PaymentTM;
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

public class PaymentController implements Initializable {

    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private ImageView MainImageview;

    @FXML
    private ImageView LogoImageview;

    @FXML
    private Label AmountLabel;

    @FXML
    private Label Payid;

    @FXML
    private Label TitleLabel;

    @FXML
    private Label PaymentidLabel;

    @FXML
    private Label DateLabel;

    @FXML
    private Label OrderItemNameLabel;

    @FXML
    private TextField AmountTextfield;

    @FXML
    private DatePicker DateTextfield;

    @FXML
    private TextField OrderItemNameTextfield;

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
    private TableView<PaymentTM> PaymentTable;

    @FXML
    private TableColumn<PaymentTM, String> paymentidColumn;

    @FXML
    private TableColumn<PaymentTM, Double> amounColumn;

    @FXML
    private TableColumn<PaymentTM, String> orderitemnameColumn;

    @FXML
    private TableColumn<PaymentTM, Date> dateColumn;

    PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        paymentidColumn.setCellValueFactory(new PropertyValueFactory<>("paymentID"));
        amounColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        orderitemnameColumn.setCellValueFactory(new PropertyValueFactory<>("orderItemName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Payment id.").show();
        }
    }

    private void refreshPage() throws SQLException,ClassNotFoundException {
        loadNextPaymentId();
        loadTableData();

        SaveButton.setDisable(false);
        UpdateButton.setDisable(true);
        DeleteButton.setDisable(true);

        AmountTextfield.setText("");
        OrderItemNameTextfield.setText("");
        DateTextfield.setValue(null);
    }

    private void loadTableData() throws SQLException ,ClassNotFoundException {
        ArrayList<PaymentDTO> paymentDTOS = paymentBO.getAllPayments();

        ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

        for (PaymentDTO paymentDTO : paymentDTOS) {
            PaymentTM paymentTM = new PaymentTM(
                    paymentDTO.getPaymentID(),
                    paymentDTO.getAmount(),
                    paymentDTO.getOrderItemName(),
                    paymentDTO.getDate()
            );
            paymentTMS.add(paymentTM);
        }
        PaymentTable.setItems(paymentTMS);
    }

    private void loadNextPaymentId() throws SQLException ,ClassNotFoundException{
        String nextPaymentId = paymentBO.generatePaymentId();
        Payid.setText(nextPaymentId);
    }

    @FXML
    void DeleteOnAction(ActionEvent event) throws SQLException , ClassNotFoundException {
        String paymentId = Payid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure , you want to delete this Payment?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = paymentBO.deletePayment(paymentId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Payment deleted....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete Payment.....!!").show();
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
    void PaymentOnMouseClicked(MouseEvent event) {
        PaymentTM paymentTM = PaymentTable.getSelectionModel().getSelectedItem();

        if (paymentTM != null) {
            Payid.setText(paymentTM.getPaymentID());
            AmountTextfield.setText(String.valueOf(paymentTM.getAmount()));
            OrderItemNameTextfield.setText(paymentTM.getOrderItemName());
            DateTextfield.setValue(paymentTM.getDate().toLocalDate());

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
    void SaveOnAction(ActionEvent event) throws SQLException ,ClassNotFoundException{
        String paymentId = Payid.getText();
        String amountString = AmountTextfield.getText();
        String orderItemName = OrderItemNameTextfield.getText();
        LocalDate date = DateTextfield.getValue();

        AmountTextfield.setStyle(AmountTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        OrderItemNameTextfield.setStyle(OrderItemNameTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        DateTextfield.setStyle(DateTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String amountPattern = "^[0-9]$";
        String orderItemNamePattern = "^[A-Za-z ]+$";
//        String datePattern = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$\n";


        boolean isValidAmount = amountString.matches(amountPattern);
        boolean isValidOrderItemName = orderItemName.matches(orderItemNamePattern);
//        boolean isValidDate = date.matches(datePattern);

        if (!isValidAmount) {
            System.out.println(AmountTextfield.getStyle());
            AmountTextfield.setStyle(AmountTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid amount...!!");
        }

        if (!isValidOrderItemName) {
            System.out.println(OrderItemNameTextfield.getStyle());
            OrderItemNameTextfield.setStyle(OrderItemNameTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid order item name...!!");
        }

        if (date == null) {
            System.out.println(DateTextfield.getStyle());
            DateTextfield.setStyle(DateTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid date...!!");
        }

        if (isValidAmount && isValidOrderItemName && date != null) {
            PaymentDTO paymentDTO = new PaymentDTO(
                    paymentId,
                    Double.parseDouble(amountString),
                    orderItemName,
                    Date.valueOf(date)
            );

            boolean isSaved = paymentBO.savePayment(paymentDTO);

            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Payment saved....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save payment....!!").show();
            }
        }
    }

    @FXML
    void UpdateOnAction(ActionEvent event) throws SQLException ,ClassNotFoundException {
        String paymentId = Payid.getText();
        String amountString = AmountTextfield.getText();
        String orderItemName = OrderItemNameTextfield.getText();
        LocalDate date = DateTextfield.getValue();

        AmountTextfield.setStyle(AmountTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        OrderItemNameTextfield.setStyle(OrderItemNameTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        DateTextfield.setStyle(DateTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String amountPattern = "^\\d{1,3}(,\\d{3})*(\\.\\d{1,2})?$\n";
        String orderItemNamePattern = "^[A-Za-z ]+$";
//        String datePattern = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$\n";


        boolean isValidAmount = amountString.matches(amountPattern);
        boolean isValidOrderItemName = orderItemName.matches(orderItemNamePattern);
//        boolean isValidDate = date.matches(datePattern);

        if (!isValidAmount) {
            System.out.println(AmountTextfield.getStyle());
            AmountTextfield.setStyle(AmountTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid amount...!!");
        }

        if (!isValidOrderItemName) {
            System.out.println(OrderItemNameTextfield.getStyle());
            OrderItemNameTextfield.setStyle(OrderItemNameTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid order item name...!!");
        }

        if (date == null) {
            System.out.println(DateTextfield.getStyle());
            DateTextfield.setStyle(DateTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid date...!!");
        }

        if (isValidAmount && isValidOrderItemName && date != null) {
            PaymentDTO paymentDTO = new PaymentDTO(
                    paymentId,
                    Double.parseDouble(amountString),
                    orderItemName,
                    Date.valueOf(date)
            );

            boolean isUpdated = paymentBO.updatePayment(paymentDTO);

            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Payment updated....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update payment....!!").show();
            }
        }
    }
}
