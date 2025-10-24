package com.example.lihini_electrical.controller;

import com.example.lihini_electrical.bo.BOFactory;
import com.example.lihini_electrical.bo.custom.CustomerBO;
import com.example.lihini_electrical.bo.custom.OrdersBO;
import com.example.lihini_electrical.bo.custom.ProductBO;
import com.example.lihini_electrical.dto.CustomerDTO;
import com.example.lihini_electrical.dto.OrdersAndProductDetailsDTO;
import com.example.lihini_electrical.dto.OrdersDTO;
import com.example.lihini_electrical.dto.ProductDTO;
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

public class OrdersController implements Initializable {

    @FXML private AnchorPane MainAnchorpane;
    @FXML private ImageView MainImageview;
    @FXML private ImageView LogoImageview;
    @FXML private Label CustomerName;
    @FXML private Label orderid;
    @FXML private DatePicker DateTextfield;
    @FXML private TextField QTYTextfield;
    @FXML private TextField QTYOnHandTextField;
    @FXML private Label ProductName;
    @FXML private Label UnitPrice;
    @FXML private ComboBox<String> ProductidCombobox;
    @FXML private ComboBox<String> CustomeridCombobox;
    @FXML private TableView<CartTM> OrdersTable;
    @FXML private TableColumn<CartTM, String> productidColumn;
    @FXML private TableColumn<CartTM, String> nameColumn;
    @FXML private TableColumn<CartTM, Integer> cartQtyColumn;
    @FXML private TableColumn<CartTM, Double> unitPricecolumn;
    @FXML private TableColumn<CartTM, Double> totalColumn;
    @FXML private TableColumn<?, ?> actionColumn;

    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

    OrdersBO ordersBO = (OrdersBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDERS);
    ProductBO productBO = (ProductBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRODUCT);
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

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

    private void refreshPage() throws SQLException, ClassNotFoundException {
        orderid.setText(ordersBO.generateOrderId());
        DateTextfield.setValue(LocalDate.now());
        loadCustomerIds();
        loadProductIds();

        CustomeridCombobox.getSelectionModel().clearSelection();
        ProductidCombobox.getSelectionModel().clearSelection();
        ProductName.setText("");
        QTYTextfield.setText("");
        QTYOnHandTextField.setText("");
        UnitPrice.setText("");
        CustomerName.setText("");
        cartTMS.clear();
        OrdersTable.refresh();
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> customerIds = customerBO.getAllIds();
        CustomeridCombobox.setItems(FXCollections.observableArrayList(customerIds));
    }

    private void loadProductIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> productIds = productBO.getAllProductIds();
        ProductidCombobox.setItems(FXCollections.observableArrayList(productIds));
    }

    @FXML
    void CustomeridComboboxOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedId = CustomeridCombobox.getSelectionModel().getSelectedItem();
        if (selectedId != null) {
            CustomerDTO customer = customerBO.searchCustomer(selectedId);
            if (customer != null) CustomerName.setText(customer.getName());
        }
    }

    @FXML
    void ProductidComboboxOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedId = ProductidCombobox.getSelectionModel().getSelectedItem();
        if (selectedId != null) {
            ProductDTO product = productBO.search(selectedId);
            if (product != null) {
                ProductName.setText(product.getName());
                UnitPrice.setText(String.valueOf(product.getPrice()));
                QTYOnHandTextField.setText(String.valueOf(product.getQuantity()));
                QTYTextfield.setText("");
            }
        }
    }

    @FXML
    void AddToCartOnAction(ActionEvent event) {
        String selectedProductId = ProductidCombobox.getValue();
        String cartQtyStr = QTYTextfield.getText();
        String qtyOnHandStr = QTYOnHandTextField.getText();

        if (selectedProductId == null || cartQtyStr.isEmpty() || qtyOnHandStr.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Select product and enter quantity!").show();
            return;
        }

        int cartQty = Integer.parseInt(cartQtyStr);
        int qtyOnHand = Integer.parseInt(qtyOnHandStr);

        if (cartQty > qtyOnHand) {
            new Alert(Alert.AlertType.ERROR, "Not enough stock!").show();
            return;
        }

        double unitPrice = Double.parseDouble(UnitPrice.getText());
        double total = cartQty * unitPrice;
        String productName = ProductName.getText();

        for (CartTM cartTM : cartTMS) {
            if (cartTM.getProductId().equals(selectedProductId)) {
                cartTM.setCartQuantity(cartTM.getCartQuantity() + cartQty);
                cartTM.setTotal(cartTM.getCartQuantity() * unitPrice);
                OrdersTable.refresh();
                QTYTextfield.clear();
                return;
            }
        }

        Button removeBtn = new Button("Remove");
        CartTM newCart = new CartTM(selectedProductId, productName, cartQty, unitPrice, total, CustomeridCombobox.getValue(), removeBtn);
        removeBtn.setOnAction(e -> {
            cartTMS.remove(newCart);
            OrdersTable.refresh();
        });

        cartTMS.add(newCart);
        OrdersTable.refresh();
        QTYTextfield.clear();
    }

    @FXML
    void PlaceOrderOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (cartTMS.isEmpty() || CustomeridCombobox.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Add products and select customer!").show();
            return;
        }

        String orderId = orderid.getText();
        Date dateOfOrder = Date.valueOf(DateTextfield.getValue());
        String customerId = CustomeridCombobox.getValue();

        ArrayList<OrdersAndProductDetailsDTO> detailsList = new ArrayList<>();
        for (CartTM cartTM : cartTMS) {
            detailsList.add(new OrdersAndProductDetailsDTO(
                    orderId,
                    cartTM.getProductId(),
                    cartTM.getCartQuantity(),
                    cartTM.getUnitPrice()
            ));
        }

        OrdersDTO orderDTO = new OrdersDTO(orderId, customerId, dateOfOrder, detailsList);

        boolean saved = ordersBO.saveOrder(orderDTO);
        if (saved) {
            new Alert(Alert.AlertType.INFORMATION, "Order placed successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to place order!").show();
        }
    }


    @FXML
    void ResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    public void GoToDashboardOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
        MainAnchorpane.getChildren().clear();
        MainAnchorpane.getChildren().add(load);
    }
}
