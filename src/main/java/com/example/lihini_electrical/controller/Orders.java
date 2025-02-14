package com.example.lihini_electrical.controller;

import com.example.lihini_electrical.bo.BOFactory;
import com.example.lihini_electrical.bo.custom.*;
import com.example.lihini_electrical.dto.*;
import com.example.lihini_electrical.tdm.CartTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Orders implements Initializable {

    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private ImageView MainImageview;

    @FXML
    private ImageView LogoImageview;

    @FXML
    private Label CustomeridLabel;

    @FXML
    private Label DateLabel;

    @FXML
    private Label CustomerName;

    @FXML
    private Label CustomerNameLabel;

    @FXML
    private Label ProductIdLabel;

    @FXML
    private Label ProductName;

    @FXML
    private Label DeliveryidLabel;

    @FXML
    private Label orderid;

    @FXML
    private Label OrderidLabel;

    @FXML
    private Label PaymentidLabel;

    @FXML
    private Label TiTleLabel;

    @FXML
    private Label ProductNameLabel;

    @FXML
    private Label UnitPrice;

    @FXML
    private Label UnitPriceLabel;

    @FXML
    private Label TotalPriceLabel;

    @FXML
    private Label StatusLabel;

    @FXML
    private DatePicker DateTextfield;

    @FXML
    private Label QTYLabel;

    @FXML
    private Label QTYOnHand;

    @FXML
    private Label QTYOnHandLabel;

    @FXML
    private TextField QTYTextfield;

    @FXML
    private Button GoToDashboardButton;

    @FXML
    private Button PlaceOrderButton;

    @FXML
    private Button AddToCartButton;

    @FXML
    private Button ResetButton;

    @FXML
    private ComboBox<String> ProductidCombobox;

    @FXML
    private ComboBox<String > CustomeridCombobox;

    @FXML
    private TableView<CartTM> OrdersTable;

    @FXML
    private TableColumn<CartTM, String> productidColumn;

    @FXML
    private TableColumn<CartTM, Integer> cartQtyColumn;

    @FXML
    private TableColumn<CartTM, String> nameColumn;

    @FXML
    private TableColumn<CartTM, Double> totalColumn;

    @FXML
    private TableColumn<CartTM, Double> unitPricecolumn;

    @FXML
    private TableColumn<?, ?> actionColumn;

    OrdersBO ordersBO = (OrdersBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDERS);
    ProductBO productBO = (ProductBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRODUCT);
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();

        try {
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
        }
    }

    private void setCellValues() {
        productidColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productname"));
        cartQtyColumn.setCellValueFactory(new PropertyValueFactory<>("cartQuantity"));
        unitPricecolumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));

        OrdersTable.setItems(cartTMS);
    }

    private void refreshPage() throws SQLException,ClassNotFoundException {
        orderid.setText(ordersBO.generateOrderId());
        DateTextfield.setValue(LocalDate.parse(LocalDate.now().toString()));

        loadCustomerIds();
        loadProductId();

        CustomeridCombobox.getSelectionModel().clearSelection();
        ProductidCombobox.getSelectionModel().clearSelection();
        ProductName.setText("");
        QTYOnHand.setText("");
        UnitPrice.setText("");
        QTYTextfield.setText("");
        CustomerName.setText("");

        cartTMS.clear();
        OrdersTable.refresh();
    }

    private void loadProductId() throws SQLException,ClassNotFoundException {
        ArrayList<String> productIds = productBO.getAllProductIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(productIds);
        ProductidCombobox.setItems(observableList);
    }

    private void loadCustomerIds() throws SQLException,ClassNotFoundException {
        ArrayList<String> customerIds = customerBO.getAllIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerIds);
        CustomeridCombobox.setItems(observableList);
    }

    @FXML
    void CustomeridComboboxOnAction(ActionEvent event) throws SQLException,ClassNotFoundException {
        String selectedCustomerId = CustomeridCombobox.getSelectionModel().getSelectedItem();
        CustomerDTO customerDTO = customerBO.searchCustomer(selectedCustomerId);

        if (customerDTO != null) {
            CustomerName.setText(customerDTO.getName());
        }
    }

    @FXML
    void GoToDashboardOnAction(ActionEvent event) throws IOException {
    AnchorPane load = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
    MainAnchorpane.getChildren().clear();
    MainAnchorpane.getChildren().add(load);
    }

    @FXML
    void AddToCartOnAction(ActionEvent event) {
        String selectedProductId = ProductidCombobox.getValue();

        if (selectedProductId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select product..!").show();
            return;
        }

        String cartQtyString = QTYTextfield.getText();

        String qtyPattern = "^[0-9]+$";

        if (!cartQtyString.matches(qtyPattern)) {
            new Alert(Alert.AlertType.ERROR, "Please enter valid quantity..!").show();
            return;
        }

        String productName = ProductName.getText();
        int cartQty = Integer.parseInt(cartQtyString);
        int qtyOnHand = Integer.parseInt(QTYOnHand.getText());

        if (qtyOnHand < cartQty) {
            new Alert(Alert.AlertType.ERROR, "Not enough products..!").show();
            return;
        }

        QTYTextfield.setText("");

        double unitPrice = Double.parseDouble(UnitPrice.getText());
        double total = unitPrice * cartQty;

        for (CartTM cartTM : cartTMS) {

            if (cartTM.getProductId().equals(selectedProductId)) {
                double newQty = cartTM.getCartQuantity() + cartQty;
                cartTM.setCartQuantity(newQty);
                cartTM.setTotal(unitPrice * newQty);

                OrdersTable.refresh();
                return;
            }
        }

        String selectedCustomerId = CustomeridCombobox.getValue();

        if (selectedCustomerId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select Customer..!").show();
            return;
        }


        Button btn = new Button("Remove");
        CartTM newCartTM = new CartTM(
                selectedProductId,
                productName,
                cartQty,
                unitPrice,
                total,
                selectedCustomerId,
                btn
        );

        btn.setOnAction(actionEvent -> {

            cartTMS.remove(newCartTM);

            OrdersTable.refresh();
        });

        cartTMS.add(newCartTM);
    }

    @FXML
    void PlaceOrderOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (OrdersTable.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add products to cart..!").show();
            return;
        }
        if (CustomeridCombobox.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select customer for place order..!").show();
            return;
        }

        String orderId = orderid.getText();
        Date dateOfOrder = Date.valueOf(DateTextfield.getValue());
        String customerId = CustomeridCombobox.getValue();

        ArrayList<OrdersAndProductDetailsDTO> ordersAndProductDetailsDTOS = new ArrayList<>();

        for (CartTM cartTM : cartTMS) {

            OrdersAndProductDetailsDTO orderDetailsDTO = new OrdersAndProductDetailsDTO(
                    cartTM.getProductId(),
                    cartTM.getProductname(),
                    cartTM.getCartQuantity(),
                    cartTM.getUnitPrice(),
                    cartTM.getTotal(),
                    cartTM.getCustomerId()
            );

            ordersAndProductDetailsDTOS.add(orderDetailsDTO);
        }

        OrdersDTO orderDTO = new OrdersDTO(
                orderId,
                customerId,
                dateOfOrder,
                ordersAndProductDetailsDTOS
        );

        boolean isSaved = ordersBO.saveOrder(orderDTO);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Order saved..!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order fail..!").show();
        }
    }

    @FXML
    void ProductidComboboxOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedProductId = ProductidCombobox.getSelectionModel().getSelectedItem();
        ProductDTO productDTO = productBO.search(selectedProductId);

        if (productDTO != null) {
            ProductName.setText(productDTO.getName());
            QTYTextfield.setText(productDTO.getQuantity().toString());
            UnitPrice.setText(String.valueOf(productDTO.getPrice()));
        }
    }

    @FXML
    void ResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
    refreshPage();
    }

}
