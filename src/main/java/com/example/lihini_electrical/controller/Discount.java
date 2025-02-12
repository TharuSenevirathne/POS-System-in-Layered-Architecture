package com.example.lihini_electrical.controller;

import com.example.lihini_electrical.bo.BOFactory;
import com.example.lihini_electrical.bo.custom.DiscountBO;
import com.example.lihini_electrical.bo.custom.OrdersBO;
import com.example.lihini_electrical.dto.DiscountDTO;
import com.example.lihini_electrical.dto.OrdersDTO;
import com.example.lihini_electrical.tdm.DiscountTM;
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

public class Discount implements Initializable {
    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private ImageView MainImageview;

    @FXML
    private ImageView LogoImageview;

    @FXML
    private Label OrderidLabel;

    @FXML
    private Label AmountLabel;

    @FXML
    private Label DiscountidLabel;

    @FXML
    private Label Disid;

    @FXML
    private Label TitleLabel;

    @FXML
    private TextField AmountTextfield;

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
    private ComboBox<String> OrderidComboBox;

    @FXML
    private TableView<DiscountTM> DiscountTable;

    @FXML
    private TableColumn<DiscountTM, Double> AmountColumn;

    @FXML
    private TableColumn<DiscountTM, String> OrderidColumn;

    @FXML
    private TableColumn<DiscountTM, String> DisidColumn;

    DiscountBO discountBO = (DiscountBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DISCOUNT);
    OrdersBO ordersBO = (OrdersBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDERS);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DisidColumn.setCellValueFactory(new PropertyValueFactory<>("discountid"));
        AmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        OrderidColumn.setCellValueFactory(new PropertyValueFactory<>("orderid"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Discount id.").show();
        }
    }

    private void refreshPage() throws SQLException,ClassNotFoundException {
        loadNextDiscountId();
        loadTableData();
        loadOrderIds();

        SaveButton.setDisable(false);
        UpdateButton.setDisable(true);
        DeleteButton.setDisable(true);

        AmountTextfield.setText("");
        OrderidComboBox.getSelectionModel().clearSelection();
    }

    private void loadOrderIds() throws SQLException ,ClassNotFoundException {
        ArrayList<String> orderIds = ordersBO.getAllOrderIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(orderIds);
        OrderidComboBox.setItems(observableList);
    }

    private void loadTableData() throws SQLException,ClassNotFoundException {
        ArrayList<DiscountDTO> discountDTOS = discountBO.getAllDiscounts();

        ObservableList<DiscountTM> discountTMS = FXCollections.observableArrayList();

        for (DiscountDTO discountDTO : discountDTOS) {
            DiscountTM discountTM = new DiscountTM(
                    discountDTO.getDiscountid(),
                    discountDTO.getAmount(),
                    discountDTO.getOrderid()
            );
            discountTMS.add(discountTM);
        }
        DiscountTable.setItems(discountTMS);
    }

    private void loadNextDiscountId() throws SQLException,ClassNotFoundException {
        String nextDiscountId = discountBO.generateDiscountId();
        Disid.setText(nextDiscountId);
    }

    @FXML
    void DeleteOnAction(ActionEvent event) throws SQLException,ClassNotFoundException {
        String discountId = Disid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure , you want to delete this Discount?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = discountBO.deleteDiscount(discountId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Discount deleted....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete discount.....!!").show();
            }
        }
    }

    @FXML
    void DiscountOnMouseClicked(MouseEvent event) {
        DiscountTM discountTM = DiscountTable.getSelectionModel().getSelectedItem();

        if (discountTM != null) {
            Disid.setText(discountTM.getDiscountid());
            AmountTextfield.setText(String.valueOf(discountTM.getAmount()));
            OrderidComboBox.getId();

            SaveButton.setDisable(true);
            DeleteButton.setDisable(false);
            UpdateButton.setDisable(false);

        }
    }

    @FXML
    void GoToDashboardOnAction(ActionEvent event) throws IOException {
    AnchorPane load = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
    MainAnchorpane.getChildren().clear();
    MainAnchorpane.getChildren().add(load);
    }

    @FXML
    void OrderidComboboxOnAction(ActionEvent event) throws SQLException,ClassNotFoundException {
        String selectedOrderId = OrderidComboBox.getSelectionModel().getSelectedItem();
        OrdersDTO ordersDTO = ordersBO.findById(selectedOrderId);

        if (ordersDTO != null) {
            System.out.println(" ");
        }
    }

    @FXML
    void ResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
    refreshPage();
    }

    @FXML
    void SaveOnAAction(ActionEvent event) throws SQLException , ClassNotFoundException {
        String discountid = Disid.getText();
        String amountString = AmountTextfield.getText();
        String orderid = OrderidComboBox.getId();

        AmountTextfield.setStyle(AmountTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String amountPattern = "^(\\\\d+)||((\\\\d+\\\\.)(\\\\d){2})$";

        boolean isValidAmount = amountString.matches(amountPattern);

        if (!isValidAmount){
            System.out.println(AmountTextfield.getStyle());
            AmountTextfield.setStyle(AmountTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid amount...!!");
        }

        if (!isValidAmount) {
            DiscountDTO discountDTO = new DiscountDTO(
                    discountid,
                    Double.parseDouble(amountString),
                    orderid
            );

            boolean isSaved = discountBO.saveDiscount(discountDTO);

            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Discount saved....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save discount....!!").show();
            }
        }
    }

    @FXML
    void UpdateOnAction(ActionEvent event) throws SQLException,ClassNotFoundException {
        String discountid = Disid.getText();
        String amountString = AmountTextfield.getText();
        String orderid = OrderidComboBox.getId();

        AmountTextfield.setStyle(AmountTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String amountPattern = "^(\\\\d+)||((\\\\d+\\\\.)(\\\\d){2})$";

        boolean isValidAmount = amountString.matches(amountPattern);

        if (!isValidAmount){
            System.out.println(AmountTextfield.getStyle());
            AmountTextfield.setStyle(AmountTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid amount...!!");
        }

        if (isValidAmount) {
            DiscountDTO discountDTO = new DiscountDTO(
                    discountid,
                    Double.parseDouble(amountString),
                    orderid
            );

            boolean isUpdated = discountBO.updateDiscount(discountDTO);

            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Discount updated....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update discount....!!").show();
            }
        }
    }
}
