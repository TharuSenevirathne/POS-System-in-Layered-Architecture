package com.example.lihini_electrical.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private ImageView LoginImageview;

    @FXML
    private ImageView LogoImageview;

    @FXML
    private ImageView PasswordImageview;

    @FXML
    private ImageView UsernameImageview;

    @FXML
    private Label LoginLabel;

    @FXML
    private Label PasswordLabel;

    @FXML
    private Label UsernameLabel;

    @FXML
    private TextField PasswordTextfield;

    @FXML
    private TextField UsernameTextfield;

    @FXML
    private Button LoginButton;

    @FXML
    private Button CancelButton;

    @FXML
    void CancelOnAction(ActionEvent event) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void LoginOnAction(ActionEvent event) throws IOException {
    String username = UsernameTextfield.getText();
    String password = PasswordTextfield.getText();

        if (username.equals("tharu") && password.equals("1234")) {
            AnchorPane load = FXMLLoader.load(getClass().getResource("/View/Introduction.fxml"));
            MainAnchorpane.getChildren().clear();
            MainAnchorpane.getChildren().add(load);
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Username or Password").show();
        }
    }
}