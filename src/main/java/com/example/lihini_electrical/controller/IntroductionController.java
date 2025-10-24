package com.example.lihini_electrical.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class IntroductionController {
    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private ImageView LogoImageview;

    @FXML
    private Text Text1;

    @FXML
    private Text Text2;

    @FXML
    private Text Text3;

    @FXML
    private Text TitleText;

    @FXML
    private Button DashboardButton;

    @FXML
    void DashboardOnAction(ActionEvent event) throws IOException {
    AnchorPane load = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
    MainAnchorpane.getChildren().clear();
    MainAnchorpane.getChildren().add(load);
    }
}
