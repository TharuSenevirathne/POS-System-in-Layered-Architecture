package com.example.lihini_electrical.controller;

import com.example.lihini_electrical.bo.BOFactory;
import com.example.lihini_electrical.bo.custom.InventoryBO;
import com.example.lihini_electrical.dto.InventoryDTO;
import com.example.lihini_electrical.tdm.InventoryTM;
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

public class InventoryController implements Initializable {
    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private ImageView MainImageview;

    @FXML
    private ImageView LogoImageview;

    @FXML
    private Label Inventoryid;

    @FXML
    private Label InventoryidLabel;

    @FXML
    private Label StockLevelLabel;

    @FXML
    private Label TitleLabel;

    @FXML
    private Label TypeLabel;

    @FXML
    private Button ResetButton;

    @FXML
    private Button SaveButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button GoToDashboardButton;

    @FXML
    private Button UpdateButton;

    @FXML
    private TextField TypeTextfield;

    @FXML
    private TextField StockLevelTextfield;

    @FXML
    private TableView<InventoryTM> InventoryTable;

    @FXML
    private TableColumn<InventoryTM, String> stockLevelColumn;

    @FXML
    private TableColumn<InventoryTM, String > typeColumn;

    @FXML
    private TableColumn<InventoryTM, String> InvenidColumn;

    InventoryBO inventoryBO = (InventoryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.INVENTORY);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InvenidColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        stockLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stocklevel"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Inventory id.").show();
        }
    }

    private void refreshPage() throws SQLException,ClassNotFoundException {
        loadNextInventoryId();
        loadTableData();

        SaveButton.setDisable(false);
        UpdateButton.setDisable(true);
        DeleteButton.setDisable(true);

        TypeTextfield.setText("");
        StockLevelTextfield.setText("");
    }

    private void loadTableData() throws SQLException,ClassNotFoundException {
        ArrayList<InventoryDTO> inventoryDTOS = inventoryBO.getAllInventories();

        ObservableList<InventoryTM> inventoryTMS = FXCollections.observableArrayList();

        for (InventoryDTO inventoryDTO : inventoryDTOS) {
            InventoryTM inventoryTM = new InventoryTM(
                    inventoryDTO.getInventoryId(),
                    inventoryDTO.getType(),
                    inventoryDTO.getStocklevel()
            );
            inventoryTMS.add(inventoryTM);
        }
        InventoryTable.setItems(inventoryTMS);
    }

    private void loadNextInventoryId() throws SQLException ,ClassNotFoundException {
        String nextInventorytId = inventoryBO.generateInventoryId();
        Inventoryid.setText(nextInventorytId);
    }

    @FXML
    void DeleteOnAction(ActionEvent event) throws SQLException,ClassNotFoundException {
        String inventoryId = Inventoryid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure , you want to delete this Inventory?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = inventoryBO.deleteInventory(inventoryId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Inventory deleted....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete inventory.....!!").show();
            }
        }
    }

    @FXML
    void GoToDashboarOnAction(ActionEvent event) throws IOException {
    AnchorPane load = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
    MainAnchorpane.getChildren().clear();
    MainAnchorpane.getChildren().add(load);
    }

    @FXML
    void InventoryOnMouseCliked(MouseEvent event) {
        InventoryTM inventoryTM = InventoryTable.getSelectionModel().getSelectedItem();

        if (inventoryTM != null) {
            TypeTextfield.setText(inventoryTM.getType());
            StockLevelTextfield.setText(inventoryTM.getStocklevel());

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
    void SaveOnAction(ActionEvent event) throws SQLException,ClassNotFoundException {
        String inventoryId = Inventoryid.getText();
        String type = TypeTextfield.getText();
        String stocklevel = StockLevelTextfield.getText();

        TypeTextfield.setStyle(TypeTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        StockLevelTextfield.setStyle(StockLevelTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String typePattern = "^[a-zA-Z]+$";
        String stockLevelPattern = "^[0-9]+$";

        boolean isValidType = type.matches(typePattern);
        boolean isValidStockLevel = stocklevel.matches(stockLevelPattern);

        if (!isValidType) {
            System.out.println(TypeTextfield.getStyle());
            TypeTextfield.setStyle(TypeTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid type...!!");
        }

        if (!isValidStockLevel) {
            System.out.println(StockLevelTextfield.getStyle());
            StockLevelTextfield.setStyle(StockLevelTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid stock level...!!");
        }

        if (isValidType && isValidStockLevel) {
            InventoryDTO inventoryDTO = new InventoryDTO(
                    inventoryId,
                    type,
                    stocklevel
            );

            boolean isSaved = inventoryBO.saveInventory(inventoryDTO);

            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Inventory saved....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save inventory....!!").show();
            }
        }
    }

    @FXML
    void UpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String inventoryId = Inventoryid.getText();
        String type = TypeTextfield.getText();
        String stocklevel = StockLevelTextfield.getText();

        TypeTextfield.setStyle(TypeTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        StockLevelTextfield.setStyle(StockLevelTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String typePattern = "^[a-zA-Z]+$";
        String stockLevelPattern = "^[0-9]+$";

        boolean isValidType = type.matches(typePattern);
        boolean isValidStockLevel = stocklevel.matches(stockLevelPattern);

        if (!isValidType) {
            System.out.println(TypeTextfield.getStyle());
            TypeTextfield.setStyle(TypeTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid type...!!");
        }

        if (!isValidStockLevel) {
            System.out.println(StockLevelTextfield.getStyle());
            StockLevelTextfield.setStyle(StockLevelTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid stock level...!!");
        }

        if (isValidType && isValidStockLevel) {
            InventoryDTO inventoryDTO = new InventoryDTO(
                    inventoryId,
                    type,
                    stocklevel
            );

            boolean isUpdated = inventoryBO.updateInventory(inventoryDTO);

            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Inventory updated....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update inventory....!!").show();
            }
        }
    }
}
