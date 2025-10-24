package com.example.lihini_electrical.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class ImageController {

    @FXML
    private AnchorPane ImageAnchorpane;

    @FXML
    private ImageView MainImageview;

    @FXML
    private Text Text1;

    @FXML
    private Text Text2;

    @FXML
    private Button nextpageButton;

    @FXML
    void nextpageOnAction(ActionEvent event) throws IOException {
    AnchorPane load = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
    ImageAnchorpane.getChildren().clear();
    ImageAnchorpane.getChildren().add(load);
    }

}
