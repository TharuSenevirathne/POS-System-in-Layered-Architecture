package com.example.lihini_electrical.controller;

import com.example.lihini_electrical.bo.BOFactory;
import com.example.lihini_electrical.bo.custom.WarrantyBO;
import com.example.lihini_electrical.tdm.WarrantyTM;
import com.example.lihini_electrical.dto.WarrantyDTO;
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

public class WarrantyController implements Initializable {
    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private ImageView MainImageview;

    @FXML
    private ImageView LogoImageview;

    @FXML
    private Label ProductNameLabel;

    @FXML
    private Label Warrantyid;

    @FXML
    private Label WarrantyidLabel;

    @FXML
    private Label WarrantyStartingDateLabel;

    @FXML
    private Label WarrantyPeriodTimeLabel;

    @FXML
    private Label TitleLabel;

    @FXML
    private TextField WarrantyPeriodTimeTextfield;

    @FXML
    private DatePicker WarrantyStartingDateTextfield;

    @FXML
    private TextField ProductNameTextfield;

    @FXML
    private Button ResetButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button GoToDashboardButton;

    @FXML
    private Button SaveButton;

    @FXML
    private Button UpdateButton;

    @FXML
    private TableView<WarrantyTM> WarrantyTable;

    @FXML
    private TableColumn<WarrantyTM, String> productnameColumn;

    @FXML
    private TableColumn<WarrantyTM, String> warrantyidColumn;

    @FXML
    private TableColumn<WarrantyTM, String> warrantyperiodtimeColumn;

    @FXML
    private TableColumn<WarrantyTM, Date> warrantystartingdateColumn; // sql date denne methanata

    WarrantyBO warrantyBO = (WarrantyBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.WARRANTY);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        warrantyidColumn.setCellValueFactory(new PropertyValueFactory<>("warrantyId"));
        productnameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        warrantyperiodtimeColumn.setCellValueFactory(new PropertyValueFactory<>("warrantyPeriodTime"));
        warrantystartingdateColumn.setCellValueFactory(new PropertyValueFactory<>("warrantyStartDate"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Warranty id.").show();
        }
    }

    private void refreshPage() throws SQLException,ClassNotFoundException {
        loadNextWarrantyId();
        loadTableData();

        SaveButton.setDisable(false);
        UpdateButton.setDisable(true);
        DeleteButton.setDisable(true);

        ProductNameTextfield.setText("");
        WarrantyPeriodTimeTextfield.setText("");
        warrantystartingdateColumn.setText("");
    }

    private void loadTableData() throws SQLException,ClassNotFoundException {
        ArrayList<WarrantyDTO> warrantyDTOS = warrantyBO.getAllWarranties();

        ObservableList<WarrantyTM> warrantyTMS = FXCollections.observableArrayList();

        for (WarrantyDTO warrantyDTO : warrantyDTOS) {
            WarrantyTM warrantyTM = new WarrantyTM(
                    warrantyDTO.getWarrantyId(),
                    warrantyDTO.getProductName(),
                    warrantyDTO.getWarrantyPeriodTime(),
                    warrantyDTO.getWarrantyStartDate()
            );
            warrantyTMS.add(warrantyTM);
        }
        WarrantyTable.setItems(warrantyTMS);
    }

    private void loadNextWarrantyId() throws SQLException,ClassNotFoundException {
        String nextWarrantyId = warrantyBO.generateWarrantyId();
        Warrantyid.setText(nextWarrantyId);
    }

    @FXML
    void DeleteOnAction(ActionEvent event) throws SQLException,ClassNotFoundException {
        String warrantyId = Warrantyid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure , you want to delete this Warranty?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = warrantyBO.deleteWarranty(warrantyId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Warranty deleted....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete warranty.....!!").show();
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
    void WarrantyOnMouseClicked(MouseEvent event) {
        WarrantyTM warrantyTM = WarrantyTable.getSelectionModel().getSelectedItem();

        if (warrantyTM != null) {
            Warrantyid.setText(warrantyTM.getWarrantyId());
            ProductNameTextfield.setText(warrantyTM.getProductName());
            WarrantyPeriodTimeTextfield.setText(warrantyTM.getWarrantyPeriodTime());
            WarrantyStartingDateTextfield.setValue(warrantyTM.getWarrantyStartDate().toLocalDate());

            SaveButton.setDisable(true);
            DeleteButton.setDisable(false);
            UpdateButton.setDisable(false);
        }
    }

    @FXML
    void SaveOnAction(ActionEvent event) throws SQLException,ClassNotFoundException {
        String warrantyId = Warrantyid.getText();
        String productName = ProductNameTextfield.getText();
        String warrantyPeriodTime = WarrantyPeriodTimeTextfield.getText();
        LocalDate localDate = WarrantyStartingDateTextfield.getValue();

        ProductNameTextfield.setStyle(ProductNameTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        WarrantyPeriodTimeTextfield.setStyle(WarrantyPeriodTimeTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        WarrantyStartingDateTextfield.setStyle(WarrantyStartingDateTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String ProductnamePattern = "^[A-Za-z ]+$";
        String warrantyPeriodTimePattern ="^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$\n";
     //   String warrantyStartingDatePattern = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$\n";

        boolean isValidProductName = productName.matches(ProductnamePattern);
        boolean isValidWarrantyPeriodTime = warrantyPeriodTime.matches(warrantyPeriodTimePattern);
     //   boolean isValidWarrantyStartingDate = localDate.matches(warrantyStartingDatePattern);

        if (!isValidProductName) {
            System.out.println(ProductNameTextfield.getStyle());
            ProductNameTextfield.setStyle(ProductNameTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid product name..!!");
        }

       if (!isValidWarrantyPeriodTime) {
           System.out.println(WarrantyPeriodTimeTextfield.getStyle());
           WarrantyPeriodTimeTextfield.setStyle(WarrantyPeriodTimeTextfield.getStyle()+ ";-fx-border-color: red;");
           System.out.println("Invalid warranty period time...!!");
       }

       if (localDate == null) {
           System.out.println(WarrantyStartingDateTextfield.getStyle());
           WarrantyStartingDateTextfield.setStyle(WarrantyStartingDateTextfield.getStyle()+ ";-fx-border-color: red;");
           System.out.println("Invalid warranty starting date...!!");
       }

        Date date = Date.valueOf(localDate);

        if (isValidProductName && isValidWarrantyPeriodTime && localDate != null) {
            WarrantyDTO warrantyDTO = new WarrantyDTO(
                    warrantyId,
                    productName,
                    warrantyPeriodTime,
                    date
            );

            boolean isSaved = warrantyBO.saveWarranty(warrantyDTO);

            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Warranty saved....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save warranty....!!").show();
            }
        }
    }

    @FXML
    void UpdateOnAction(ActionEvent event) throws SQLException ,ClassNotFoundException{
        String warrantyId = Warrantyid.getText();
        String productName = ProductNameTextfield.getText();
        String warrantyPeriodTime = WarrantyPeriodTimeTextfield.getText();
        LocalDate localDate = WarrantyStartingDateTextfield.getValue();

        ProductNameTextfield.setStyle(ProductNameTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        WarrantyPeriodTimeTextfield.setStyle(WarrantyPeriodTimeTextfield.getStyle() + ";-fx-border-color: #7367F0;");
        WarrantyStartingDateTextfield.setStyle(WarrantyStartingDateTextfield.getStyle() + ";-fx-border-color: #7367F0;");

        String ProductnamePattern = "^[A-Za-z ]+$";
        String warrantyPeriodTimePattern ="^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$\n";
     //   String warrantyStartingDatePattern = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$\n";

        boolean isValidProductName = productName.matches(ProductnamePattern);
        boolean isValidWarrantyPeriodTime = warrantyPeriodTime.matches(warrantyPeriodTimePattern);
      //  boolean isValidWarrantyStartingDate = warrantyStartingDate.matches(warrantyStartingDatePattern);

        if (!isValidProductName) {
            System.out.println(ProductNameTextfield.getStyle());
            ProductNameTextfield.setStyle(ProductNameTextfield.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid product name..!!");
        }

        if (!isValidWarrantyPeriodTime) {
            System.out.println(WarrantyPeriodTimeTextfield.getStyle());
            WarrantyPeriodTimeTextfield.setStyle(WarrantyPeriodTimeTextfield.getStyle()+ ";-fx-border-color: red;");
            System.out.println("Invalid warranty period time...!!");
        }

        if (localDate == null) {
            System.out.println(WarrantyStartingDateTextfield.getStyle());
            WarrantyStartingDateTextfield.setStyle(WarrantyStartingDateTextfield.getStyle()+ ";-fx-border-color: red;");
            System.out.println("Invalid warranty starting date...!!");
        }

        Date date = Date.valueOf(localDate);

        if (isValidProductName && isValidWarrantyPeriodTime && localDate != null) {
            WarrantyDTO warrantyDTO = new WarrantyDTO(
                    warrantyId,
                    productName,
                    warrantyPeriodTime,
                    date
            );

            boolean isUpdated = warrantyBO.updateWarranty(warrantyDTO);

            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Warranty updated....").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update warranty....!!").show();
            }
        }
    }

}

